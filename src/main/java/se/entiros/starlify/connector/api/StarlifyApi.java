package se.entiros.starlify.connector.api;

import se.entiros.starlify.connector.model.starlify.System;

public interface StarlifyApi {
  System createSystem(String starlifyApiKey, System system);

  void createServices(
      String starlifyApiKey, String systemId, String ramlFileName, String ramlApiDefinition);
}
