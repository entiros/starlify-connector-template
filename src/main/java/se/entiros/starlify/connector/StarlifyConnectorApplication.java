package se.entiros.starlify.connector;

import com.starlify.connector.EnableStarlifyConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableStarlifyConnector
public class StarlifyConnectorApplication {

  public static void main(String[] args) {
    SpringApplication.run(StarlifyConnectorApplication.class, args);
  }
}
