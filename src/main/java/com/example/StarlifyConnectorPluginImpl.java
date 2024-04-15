package com.example;

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
  public List<StarlifySystem> getSystems() {
    // TODO Auto-generated method stub
    // Fetch systems from the data source.
    // Build StarlifySystem objects. Each StarlifySystem must have an externalId and a name.
    // A StarlifySystem can also have a description.
    // If this system is a subsystem, it can have a parentExternalId mapping to the parent system
    // All the StarlifySystem objects must be returned in a list.
    throw new UnsupportedOperationException("Unimplemented method 'getSystems'");
  }
  @Override
  public List<StarlifyService> getServices() {
    // TODO Auto-generated method stub
    // Fetch services from the data source.
    // Build StarlifyService objects. Each StarlifyService must have an externalId and a name.
    // A StarlifyService must also have a providerExternalId mapping
    // to the provider system. The provider system must have either been created in the getSystems
    // method or be a system that already exists in Starlify.
    // All the StarlifyService objects must be returned in a list.
    throw new UnsupportedOperationException("Unimplemented method 'getServices'");
  }

  @Override
  public List<StarlifyReference> getReferences() {
    // TODO Auto-generated method stub
    // Fetch references from the data source.
    // Build StarlifyReference objects. Each StarlifyReference must have an externalId and a name.
    // A StarlifyReference must also have a sourceExternalId mapping
    // to the source system. The source system must have either been created in
    // the getSystems method or be a system that already exists in Starlify.
    // The StarlifyReference must also have a target Identifiable with an external id mapping to the
    // target service. The target service must have either been created in the getServices method or
    // be a service that already exists in Starlify.
    // All the StarlifyReference objects must be returned in a list.
    throw new UnsupportedOperationException("Unimplemented method 'getReferences'");
  }

}
