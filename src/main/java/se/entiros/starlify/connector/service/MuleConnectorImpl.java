package se.entiros.starlify.connector.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.entiros.starlify.connector.api.MuleApi;
import se.entiros.starlify.connector.api.StarlifyApi;
import se.entiros.starlify.connector.model.mule.Asset;
import se.entiros.starlify.connector.model.mule.MuleImportRequest;
import se.entiros.starlify.connector.model.starlify.System;

@Slf4j
@Service
@RequiredArgsConstructor
public class MuleConnectorImpl implements StarlifyConnector<MuleImportRequest> {

  private final MuleApi muleApi;
  private final StarlifyApi starlifyApi;

  @Override
  public void importDataIntoStarlify(MuleImportRequest request) {
    muleApi
        .getAssetList(request.getApiKey())
        .forEach(asset -> importAssetIntoStarlify(request, asset));
  }

  private void importAssetIntoStarlify(MuleImportRequest request, Asset asset) {
    try {
      log.info("Processing asset <{}>", asset);

      String systemId =
          starlifyApi
              .createSystem(
                  request.getStarlifyKey(),
                  System.from(request.getNetworkId(), asset.getName(), asset.getDescription()))
              .getId();

      String ramlApiDefinition =
          muleApi.getRaml(
              request.getApiKey(),
              asset.getVersionGroup(),
              asset.getGroupId(),
              asset.getAssetId(),
              asset.getVersion());
      String ramlFileName = asset.getAssetId() + ".raml";

      starlifyApi.createServices(
          request.getStarlifyKey(), systemId, ramlFileName, ramlApiDefinition);
    } catch (Throwable throwable) {
      log.error("Error while processing asset <{}>", asset.getName(), throwable);
    }
  }
}
