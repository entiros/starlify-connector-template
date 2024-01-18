package se.entiros.starlify.connector.mule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import se.entiros.starlify.connector.mule.model.MuleImportRequest;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class MuleConnectorController {

  private final MuleConnector muleConnector;

  @PostMapping("/connector/mulesoft/import")
  public void importDataIntoStarlify(@RequestBody MuleImportRequest muleImportRequest) {
    muleConnector.importDataToStarlify(muleImportRequest);
  }
}
