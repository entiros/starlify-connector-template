package se.entiros.starlify.connector.model.mule;

import lombok.Data;

@Data
public class MuleImportRequest {
  private String starlifyKey;
  private String apiKey;
  private String networkId;
}
