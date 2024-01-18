package se.entiros.starlify.connector.mule.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.entiros.starlify.connector.mule.model.Asset;
import se.entiros.starlify.connector.mule.model.UserProfile;

@Service
@RequiredArgsConstructor
public class MuleApiImpl implements MuleApi {

  private final RestTemplate restTemplate;

  @Value("${mulesoft.server.url}")
  private String muleUrl;

  @Override
  public UserProfile getUserProfile(String muleAccessToken) {
    return restTemplate
        .exchange(
            muleUrl + "/accounts/api/me",
            HttpMethod.GET,
            new HttpEntity<>(null, toHeaders(muleAccessToken)),
            new ParameterizedTypeReference<UserProfile>() {})
        .getBody();
  }

  @Override
  public List<Asset> getAssets(String muleAccessToken, String orgId) {
    return restTemplate
        .exchange(
            muleUrl + "/exchange/api/v2/assets/search?masterOrganizationId={orgId}",
            HttpMethod.GET,
            new HttpEntity<>(null, toHeaders(muleAccessToken)),
            new ParameterizedTypeReference<List<Asset>>() {},
            orgId)
        .getBody();
  }

  @Override
  public String getRaml(
      String accessToken, String versionGroup, String groupId, String assetId, String version) {
    return restTemplate
        .exchange(
            muleUrl + "/exchange/api/{versionGroup}/assets/{groupId}/{assetId}/{version}/api/root",
            HttpMethod.GET,
            new HttpEntity<>(null, toHeaders(accessToken)),
            new ParameterizedTypeReference<String>() {},
            versionGroup,
            groupId,
            assetId,
            version)
        .getBody();
  }

  private static HttpHeaders toHeaders(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);
    return headers;
  }
}
