package se.entiros.starlify.connector.api;

import java.util.List;
import se.entiros.starlify.connector.model.mule.Asset;

public interface MuleApi {
  List<Asset> getAssetList(String accessToken);

  String getRaml(
      String accessToken, String versionGroup, String groupId, String assetId, String version);
}
