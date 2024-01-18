package se.entiros.starlify.connector.starlify.model;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class System extends Named {
  private String description;
  private Network network;

  public static System from(String networkId, String name, String description) {
    System system = new System();
    String id = UUID.randomUUID().toString();
    system.setId(id);
    system.setName(name);
    Network network = new Network();
    network.setId(networkId);
    system.setNetwork(network);
    system.setDescription(description);
    return system;
  }
}
