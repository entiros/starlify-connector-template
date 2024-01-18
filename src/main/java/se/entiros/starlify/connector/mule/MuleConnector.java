package se.entiros.starlify.connector.mule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.entiros.starlify.connector.mule.api.MuleApi;
import se.entiros.starlify.connector.mule.model.Asset;
import se.entiros.starlify.connector.mule.model.MuleImportRequest;
import se.entiros.starlify.connector.starlify.api.StarlifyApi;
import se.entiros.starlify.connector.starlify.model.System;

@Slf4j
@Service
@RequiredArgsConstructor
public class MuleConnector {

  private final MuleApi muleApi;
  private final StarlifyApi starlifyApi;

  public void importDataToStarlify(MuleImportRequest request) {
    String organizationId =
        muleApi.getUserProfile(request.getMuleAccessToken()).getUser().getOrganization().getId();

    muleApi
        .getAssets(request.getMuleAccessToken(), organizationId)
        .forEach(asset -> importAssetIntoStarlify(request, asset));
  }

  private void importAssetIntoStarlify(MuleImportRequest request, Asset asset) {
    try {
      log.info("Importing asset <{}>", asset);

      String systemId =
          starlifyApi
              .createSystem(
                  request.getStarlifyApiKey(),
                  System.from(request.getNetworkId(), asset.getName(), asset.getDescription()))
              .getId();

      String ramlApiDefinition =
          muleApi.getRaml(
              request.getMuleAccessToken(),
              asset.getVersionGroup(),
              asset.getGroupId(),
              asset.getAssetId(),
              asset.getVersion());
      String ramlFileName = asset.getAssetId() + ".raml";

      starlifyApi.createServices(
          request.getStarlifyApiKey(), systemId, ramlFileName, ramlApiDefinition);
    } catch (Throwable throwable) {
      log.error("Failed to import asset <{}>", asset.getName(), throwable);
    }
  }
}
