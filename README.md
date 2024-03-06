# Starlify connector

## Description

This is a skeleton project for building your own custom Starlify connector. The connector can then be used with Starlify to automatically import systems, services and references from your chosen data source directly into a Starlify network.

## Dependencies
TODO

## Building your own connector
This skeleton uses the Starlify Connector SDK as a dependency. This SDK provides all the basic requirements for building a custom connector.
It sets up a server that will listen for REST calls from Starlify to a specific endpoint on the connector. This endpoint in turn will 
call the methods in the connector that you will have to implement. The methods are `getSystems`, `getServices` and `getReferences`.
These methods are defined in the StarlifyConnectorPlugin interface, and your implementation of them must be added to the StarlifyConnectorPluginImpl class.
The class is already created in the skeleton, but the three methods have placeholder comments that should replace with your code.

The methods you should implement are:
- `getSystems()`: This method should return a list of `StarlifySystem` objects representing the systems in your data source.
- `getServices()`: This method should return a list of `StarlifyService` objects representing the services in your data source.
- `getReferences()`: This method should return a list of `StarlifyReference` objects representing the references in your data source.

The `StarlifySystem`, `StarlifyService` and `StarlifyReference` classes are provided in the SDK and should be used to create the objects you return from the methods. These classes are documented in the Models section.

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
     - `description`: A description of the system.

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
     - `description`: A description of the service.

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
     - `description`: A description of the reference.

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

## External IDs
Importing entities from an external data source into Starlify relies on the concept of *external IDs*. Every external node that is to be entered into Starlify must have an external ID. This ID is used to identify the node from the external data source and is used to match the node in the external data source with the node in Starlify. The external ID must be unique within the workspace, and it must be deterministic for a specific node, meaning every time that node is imported, the external ID must be the same as the previous time. If an import enters a node with an external ID that already exists in the workspace, the node will be updated with the new data. If the external ID does not exist in the workspace, a new node will be created.

### Acceptable external IDs
Acceptable external IDs could for example be unique IDs associated with the node in the external data source, such as a database ID, a UUID identifier, or a unique name that will not change.

### Unacceptable external IDs
Unacceptable external IDs could for example be a name that is not unique, a name that can change, or a name that is not deterministic, such as a UUID generated during the import process.

## Configuration
Configuration options for the connector are specified in the `application.yaml` file in the `src/main/resources` directory. You will need to provide your Starlify API key in this file, which you can get under 'External Connections' in Starlify.

## Installation
To install the project with the included Maven wrapper, follow these steps:

1. Clone the repository: `git clone https://github.com/entiros/starlify-connector.git`
2. Follow the instructions described in the Installation and Configuration sections.
3. In the project directory, run the Maven Wrapper script:

   - On Unix/Mac OS: `./mvnw clean install`
   - On Windows: `mvnw.cmd clean install`

   Or, if you have Maven installed, you can run: `mvn clean install`

## Usage
To use a custom connector in Starlify you will have to follow these steps:
