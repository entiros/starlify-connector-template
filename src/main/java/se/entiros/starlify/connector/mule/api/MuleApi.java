package se.entiros.starlify.connector.mule.api;

import java.util.List;
import se.entiros.starlify.connector.mule.model.Asset;
import se.entiros.starlify.connector.mule.model.UserProfile;

public interface MuleApi {

  UserProfile getUserProfile(String muleAccessToken);

  List<Asset> getAssets(String muleAccessToken, String orgId);

  String getRaml(
      String accessToken, String versionGroup, String groupId, String assetId, String version);
}
