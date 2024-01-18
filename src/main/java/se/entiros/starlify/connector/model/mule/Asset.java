package se.entiros.starlify.connector.model.mule;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Asset {
  private String groupId;
  private String assetId;
  private String version;
  private String versionGroup;
  private String description;
  private String name;
}
