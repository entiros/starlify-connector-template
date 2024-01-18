package se.entiros.starlify.connector.starlify.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import se.entiros.starlify.connector.starlify.model.System;

@Service
@RequiredArgsConstructor
public class StarlifyApiImpl implements StarlifyApi {

  public static final String X_API_KEY = "X-API-KEY";

  private final RestTemplate restTemplate;

  @Value("${starlify.url}")
  private String starlifyApiUrl;

  @Override
  public System createSystem(String starlifyApiKey, System system) {
    return restTemplate
        .exchange(
            starlifyApiUrl + "/hypermedia/networks/{networkId}/systems",
            HttpMethod.POST,
            new HttpEntity<>(system, toHeaders(starlifyApiKey)),
            new ParameterizedTypeReference<System>() {},
            system.getNetwork().getId())
        .getBody();
  }

  @Override
  public void createServices(
      String starlifyApiKey, String systemId, String ramlFileName, String ramlApiDefinition) {
    restTemplate.exchange(
        starlifyApiUrl + "/hypermedia/systems/{systemId}/services",
        HttpMethod.PUT,
        toHttpEntity(starlifyApiKey, ramlFileName, ramlApiDefinition),
        new ParameterizedTypeReference<String>() {},
        systemId);
  }

  private static HttpEntity<MultiValueMap<String, Object>> toHttpEntity(
      String starlifyApiKey, String fileName, String ramlApiDefinition) {
    MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
    ContentDisposition contentDisposition =
        ContentDisposition.builder("form-data").name("file").filename(fileName).build();
    fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
    HttpEntity<byte[]> fileEntity = new HttpEntity<>(ramlApiDefinition.getBytes(), fileMap);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("file", fileEntity);

    HttpHeaders headers = toHeaders(starlifyApiKey);
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    return new HttpEntity<>(body, headers);
  }

  private static HttpHeaders toHeaders(String starlifyApiKey) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(X_API_KEY, starlifyApiKey);
    return headers;
  }
}
