# Starlify connector

## Description
This is a template project for building your own custom Starlify connector. 

A Starlify Connector is a Spring Boot server application that exposes HTTP endpoints that can be used to trigger data synchronization from external data sources to Starlify (www.starlify.com). It starts an import job when an HTTP endpoint is called with an API key and a network ID, whereupon it imports systems, services and references from an external data source which exposes this data via the implementation of the StarlifyConnectorPlugin interface.

It requires Java 21.

## Usage
To start using a custom connector in Starlify you will have to follow these steps:

### Prepare the connector

1. Clone the connector template repository: `git clone https://github.com/entiros/starlify-connector-template.git`
2. Implement the methods in the StarlifyConnectorPluginImpl class to retrieve systems, services and references from your data source. See the [Building and testing your connector](#building-and-testing-your-connector) section for more information.
3. Build the project, either using maven: `mvn clean install`.
   - If you have maven installed: `mvn clean install`
   - If you don't have maven installed, you can use the Maven Wrapper script:
     - On Unix/Mac OS: `./mvnw clean install`
     - On Windows: `mvnw.cmd clean install`
4. Start the connector by running `mvn spring-boot:run` in the project directory. If you don't have maven installed, you can use mvnw as described in the previous step.

### Create an external connection in Starlify

1. In Starlify, go to the `External Connections` page.
2. Click `Create external connection`.
3. When prompted to select connector type, choose `Custom connector`.
4. Type in a name (required) and a description (optional) for the connector.
5. Type in the URL of the connector (required). This is the URL of the connector's endpoint. If you are running the connector locally, the URL will be `http://localhost:8080/`.
6. Click the 'next' arrow. The connection will be created. In the next tab you will see the connector information and the API key. Copy the API key and store it somewhere safe, you will not be able to see it again once you close the window.
7. In your connector's `src/resources/application.yaml` file, replace the `starlify-api.api-key` value with the API key you just copied.
8. Start the connector by running `mvn spring-boot:run` in the project directory.
9. In Starlify, click `Verify` to ensure that your connector is set up correctly.

### Import data from the connector to Starlify

#### Trigger import from inside Starlify
1. In Starlify, go to the `External Connections` page.
2. In the list of Agents, select the connector you want to import data from.
3. Select the network that the data should be imported into.
4. Click `Sync import`. Starlify will call the connector to start the import process. The connector will extract data from the external data source and send the data to Starlify.
5. Click the refresh button in the `Import history` to see the status of import jobs.
6. If any errors occur during the import, check the connector's logs for more information.

#### Trigger import manually
It is also possible to trigger the import manually by calling the import endpoint. This can be done for example using Postman or curl. Depending on your needs, you may also want to set up something that calls the import repeatedly with a given interval to sync data regularly. 

To trigger the endpoint, make a POST request to the URL [[locationOfServer:port]/connector/networks/{networkId}/import](#), where the networkId needs to be replaced with the UUID of the network you want to import data to. This can be found in the network's details pane in Starlify. A header "X-API-KEY" is also needed, with the value set to the API key you got when setting up the connector in Starlify.

Note that if you want to trigger the import when the connector is in [test mode](#test-mode), the endpoint is the same, but the networkId can be any valid UUID, and the X-API-KEY header can be empty.

## Building and testing your connector
This template uses the Starlify Connector SDK as a dependency. The SDK provides all the basic essentials for building a custom connector. It sets up a server with an endpoint that will listen for REST calls from Starlify. This endpoint will then call methods in the connector to get systems, services and references. These methods are defined in the StarlifyConnectorPlugin interface, and an implementation of them must be added to the StarlifyConnectorPluginImpl class. The class is already created in the template, but the three methods have placeholder code that should be replaced with your code. Your code should fetch the systems, services and references from your data source and return them as lists of StarlifySystem, StarlifyService and StarlifyReference objects.

The methods to implement are:

- `getSystems()`: This method should return a list of `StarlifySystem` objects representing the systems in your data source.
- `getServices()`: This method should return a list of `StarlifyService` objects representing the services in your data source.
- `getReferences()`: This method should return a list of `StarlifyReference` objects representing the references in your data source.

The `StarlifySystem`, `StarlifyService` and `StarlifyReference` classes are provided in the SDK and should be used to create the objects you return from the methods. These classes are documented in the [Models](#models) section.

### External IDs

Importing entities from an external data source into Starlify relies on the concept of _external IDs_. Every external node that is to be entered into Starlify must have an external ID. This ID is used to identify the node from the external data source and is used to match the node in the external data source with the node in Starlify. The external ID must be unique within the workspace, and it must be deterministic for a specific node, meaning every time that node is imported, the external ID must be the same as the previous time. If an import enters a node with an external ID that already exists in the workspace, the node will be updated with the new data. If the external ID does not exist in the workspace, a new node will be created.

### Acceptable external IDs

Acceptable external IDs could for example be unique IDs associated with the node in the external data source, such as a database ID, a UUID identifier, or a unique name that will not change.

### Unacceptable external IDs

Unacceptable external IDs could for example be a name that is not unique, a name that can change, or a name that is not deterministic, such as a UUID generated during the import process.

### Test mode
When you are building the connector and implementing the required methods, you may not want to connect the connector to Starlify from the beginning. For this purpose, the connector may be started in test mode. There are three ways to start the connector in test mode. Any of these ways will ensure the connector starts in test mode:
1. Keep the value starlify-api.api-key in the `application.yaml` file set to `INSERT_API_KEY_HERE.
2. Remove the starlify-api.api-key value from the `application.yaml` file.
3. Add the property starlify-api.testing with the value set to `true` in the `application.yaml` file.

- Keep the value starlify-api.api-key in the `application.yaml` file set to `INSERT_API_KEY_HERE.
- Remove the starlify-api.api-key value from the `application.yaml` file.
- Add the property starlify-api.testing with the value set to `true` in the `application.yaml` file.

When the connector is started in test mode, it will not connect to Starlify, and the connector will not reqbent to Starlify. This allows you to verify that the connector is extracting data from the external data source correctly, and that the data is being converted to StarlifySystems, StarlifyServices and StarlifyReferences correctly. To call the connector you can use for example Postman with a POST request to the URL [[locationOfServer:port]/connector/networks/{networkId}/import](#), where the networkId needs to be replaced with any valid UUID, for example 853c2d40-e738-4196-a199-33f2c6225fa0. A header "X-API-KEY" is also needed, but the value can empty.

### Models

1. **`Identifiable`**

   - Description: This class represents an identifiable entity in the Starlify connector.
   - Usage: The `Identifiable` class is used to create objects with a unique external identifier. It should be used in specific cases where the type of the object is not known. For example, it can be used as a target in a `StarlifyReference` object, where the target could be either a `StarlifyService` or a `StarlifyEndpoint`.

   - Example instantiation:

     ```java
     import com.starlify.connector.model.Identifiable;

     // Example instantiation of Identifiable
     Identifiable identifiable = Identifiable.builder()
         .externalId("exampleExternalId")
         .build();
     ```

2. **`StarlifySystem`**

   - Description: This model represents a system in Starlify.
   - Usage: The `StarlifySystem` model is used to create systems with the following attributes:

     - `externalId`: The external identifier for the system. This should be something **unique** and **deterministic** that can identify the specified system from the external data source.
     - `name`: The name of the system. The name should be unique among the other systems in a network.
     - `description`: A description of the system (optional).

   - Example instantiation:

     ```java
     import com.starlify.connector.model.StarlifySystem;

     // Example instantiation of a StarlifySystem
     StarlifySystem system = StarlifySystem.builder()
         .externalId("exampleSystemExternalId")
         .name("Example System")
         .description("Example system description")
         .build();
     ```

3. **`StarlifyService`**

   - Description: This model represents a service in Starlify.
   - Usage: The `StarlifyService` model is used to create services with the following attributes:

     - `externalId`: The external identifier for the service. This should be something **unique** and **deterministic** that can identify the specified service from the external data source.
     - `name`: The name of the service. The name should be unique among the other services with the same `StarlifySystem` as provider.
     - `providerExternalId`: The external identifier of the service's provider. This should be the externalId of the `StarlifySystem` that provides the service.
     - `description`: A description of the service (optional).

   - Example instantiation:

     ```java
     import com.starlify.connector.model.StarlifyService;

     // Example instantiation of a StarlifyService
     StarlifyService service = StarlifyService.builder()
         .externalId("exampleServiceExternalId")
         .name("Example Service")
         .providerExternalId("exampleExternalSystemIdentifier")
         .description("Example service description")
         .build();
     ```

4. **`StarlifyReference`**

   - Description: This model represents a reference in Starlify.
   - Usage: The `StarlifyReference` model is used to create references with the following attributes:

     - `externalId`: The external identifier for the reference. This should be something **unique** and **deterministic** that can identify the specified reference from the external data source.
     - `name`: The name of the reference. The name should be unique among the other references with the same `StarlifySystem` as source.
     - `sourceExternalId`: The external identifier of the reference's source. This should be the externalId of the `StarlifySystem` that is the source of the reference.
     - `target`: The `StarlifyService` that the reference targets. The target is identified by an `Identifiable` with the same externalId as the `StarlifyService` the reference should target.
     - `description`: A description of the reference (optional).

   - Example instantiation:

     ```java
     import com.starlify.connector.model.Identifiable;
     import com.starlify.connector.model.StarlifyReference;

     // Example instantiation of a StarlifyReference
     StarlifyReference reference = StarlifyReference.builder()
         .externalId("exampleReferenceExternalId")
         .name("Example Reference")
         .sourceExternalId("exampleExternalSystemIdentifier")
         .target(Identifiable.builder().externalId("anotherExampleServiceExternalId").build())
         .description("Example description")
         .build();
     ```

## Configuration
Configuration options for the connector are specified in the `application.yaml` file in the `src/main/resources` directory. You will need to provide your Starlify API key in this file, which you can get under 'External Connections' in Starlify.

The `application.yaml` file contains the following properties:

```yaml
starlify-api:
  api-key: INSERT_API_KEY_HERE
  testing: false
  
server:
  port: 8080
```

### Explanation of properties
- `starlify-api.api-key`: The API key for the connector. This key is used to authenticate the connector with Starlify. You can get the API key from the 'External Connections' page in Starlify when you create a new connection.
- `starlify-api.testing`: A boolean value that determines whether the connector is running in test mode. If this value is set to `true`, the connector will not connect to Starlify, and the connector will not require an API key. See more information about this in the [test mode](#test-mode) section.
- `server.port`: The port that the connector will listen on. This value can be changed if port 8080 is already in use.
