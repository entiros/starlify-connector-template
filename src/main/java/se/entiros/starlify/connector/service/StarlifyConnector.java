package se.entiros.starlify.connector.service;

public interface StarlifyConnector<ImportRequest> {
  void importDataIntoStarlify(ImportRequest importRequest);
}
