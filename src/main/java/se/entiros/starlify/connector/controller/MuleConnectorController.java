package se.entiros.starlify.connector.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import se.entiros.starlify.connector.model.mule.MuleImportRequest;
import se.entiros.starlify.connector.service.StarlifyConnector;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class MuleConnectorController {

  private final StarlifyConnector<MuleImportRequest> muleConnector;

  @PostMapping("/connector/mulesoft/import")
  public void importDataIntoStarlify(@RequestBody MuleImportRequest muleImportRequest) {
    muleConnector.importDataIntoStarlify(muleImportRequest);
  }
}
