package se.entiros.starlify.connector.mule.model;

import lombok.Data;

@Data
public class MuleImportRequest {
  private String starlifyApiKey;
  private String networkId;
  private String muleAccessToken;
}
