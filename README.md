# Starlify connector

## Description

This is a skeleton project to build your own custom Starlify connector upon. The connector can then be used in Starlify to automatically import systems, services and references from your chosen data source directly into a network in Starlify.

## Dependencies

## Implementation

This project uses the Starlify Connector SDK, which provides several models that should be used in this project. Detailed documentation for these models can be found in the Models section.

The `StarlifyConnectorPluginImpl` class in this project implements the `StarlifyConnectorPlugin` interface from the SDK and currently has placeholder implementations for the following methods:

- `getReferences()`: This method should return a list of `StarlifyReference` objects representing the references in your data source.
- `getServices()`: This method should return a list of `StarlifyService` objects representing the services in your data source.
- `getSystems()`: This method should return a list of `StarlifySystem` objects representing the systems in your data source.

Replace these placeholder implementations with your own code that retrieves the relevant data from your data source and returns it in the specified format.

### Models

1. **`Identifieable`**

   - Description: This class represents an identifiable entity in the Starlify connector.
   - Usage: The `Identifieable` class is used to create objects with a unique external identifier.

   - Example Implementation:

     ```java
     import com.starlify.connector.model.Identifieable;

     // Example instantiation of Identifieable
     Identifieable identifiable = Identifieable.builder()
         .externalId("exampleExternalId")
         .build();
     ```

2. **`StarlifySystem`**

   - Description: This model represents a system in Starlify.
   - Usage: The `StarlifySystem` model is used to create systems with the following attributes:

     - `externalId`: The external identifier for the system. This should be something unique that can identify the specified system from the external data source. The uniqueness is constrained within a workspace in Starlify.
     - `name`: The name of the system. The name should be unique among the other systems in a network.
     - `description`: A description of the system.

   - Example Implementation:

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

     - `externalId`: The external identifier for the service. This should be something unique that can identify the specified service from the external data source. The uniqueness is constrained within the `StarlifySystem` that provides the service in Starlify.
     - `name`: The name of the service. The name should be unique among the other services with the same `StarlifySystem` as provider.
     - `providerExternalId`: The external identifier of the service's provider. This should be the externalId of the `StarlifySystem` that provides the service.
     - `description`: A description of the service.

   - Example Implementation:

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

     - `externalId`: The external identifier for the reference. This should be something unique that can identify the specified reference from the external data source. The uniqueness is constrained within the `StarlifySystem` that is the source of the reference in Starlify.
     - `name`: The name of the reference. The name should be unique among the other references with the same `StarlifySystem` as source.
     - `sourceExternalId`: The external identifier of the reference's source. This should be the the externalId of the `StarlifySystem` that is the source of the reference.
     - `target`: The `StarlifyService` that the reference targets. The target is identified by an `Identifieable` with the same externalId as the `StarlifyService` the reference should target.
     - `description`: A description of the reference.

   - Example Implementation:

     ```java
     import com.starlify.connector.model.Identifieable;
     import com.starlify.connector.model.StarlifyReference;

     // Example instantiation of a StarlifyReference
     StarlifyReference reference = StarlifyReference.builder()
         .externalId("exampleReferenceExternalId")
         .name("Example Reference")
         .sourceExternalId("exampleExternalSystemIdentifier")
         .target(Identifieable.builder().externalId("anotherExampleServiceExternalId").build())
         .description("Example description")
         .build();
     ```

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

## License

Information about the project's license.
