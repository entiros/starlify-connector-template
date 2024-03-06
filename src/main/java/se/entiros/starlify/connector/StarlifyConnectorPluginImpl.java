package se.entiros.starlify.connector;

import com.starlify.connector.interfaces.StarlifyConnectorPlugin;
import com.starlify.connector.model.StarlifyReference;
import com.starlify.connector.model.StarlifyService;
import com.starlify.connector.model.StarlifySystem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StarlifyConnectorPluginImpl implements StarlifyConnectorPlugin {
  @Override
  public List<StarlifyReference> getReferences() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getReferences'");
  }

  @Override
  public List<StarlifyService> getServices() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getServices'");
  }

  @Override
  public List<StarlifySystem> getSystems() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getSystems'");
  }
}
