package se.entiros.starlify.connector.starlify.api;

import se.entiros.starlify.connector.starlify.model.System;

public interface StarlifyApi {
  System createSystem(String starlifyApiKey, System system);

  void createServices(
      String starlifyApiKey, String systemId, String ramlFileName, String ramlApiDefinition);
}
