# Architectural Decision Record: CFP Package Refactoring

## Context

The Call for Papers (CFP) module is a critical component of our conference management system, handling the submission and review of session proposals. 

## Architectural Style
The application follows a Hexagonal Architecture (also known as Ports and Adapters) within a DDD context:

- **Domain Layer**: Contains the core business logic, entities, value objects, and domain services
- **Application Layer**: Orchestrates the use cases of the application
- **Infrastructure Layer**: Provides implementations for persistence, API endpoints, and integration with external systems
- **Anti-Corruption Layer**: Translates between our domain model and external systems

## Key Components

### Domain Layer
- **Aggregates**: `Speaker` - the main aggregate root
- **Value Objects**: `ConferenceSessionAbstract`
- **Domain Services**: `SpeakerService` - handles business operations on speakers, `ConferenceSessionAbstractService` - manages conference sessions
- **Domain Events**: Events like `SpeakerRegistrationEvent`, `SpeakerUpdatedEvent`, and `AbstractSubmittedEvent`

### Application Layer
- **Commands**: `RegisterSpeakerCommand` - represents intent to register an attendee
- **Events**: `A` - domain events triggered by registration

### Infrastructure Layer
- **Persistence**: JPA entities and Hibernate Panache repositories for storing attendee data
- **API Endpoints**: REST endpoints for attendee registration and lookup
- **Event Publishers**: Components that publish domain events for integration with other bounded contexts

## Technology Stack
- **Framework**: Quarkus
- **Persistence**: Hibernate with Panache
- **API**: JAX-RS REST endpoints
- **Messaging**: Reactive messaging for event handling
- **Containerization**: Docker with OpenShift deployment support
- **Build Tool**: Maven

## Bounded Contexts
The system is organized around the following bounded contexts:
- **Shared Kernel**: Contains shared components used across bounded contexts. `SessionAcceptedEvent` is an event that is used to communicate with the "agenda" bounded context.
- **Call for Papers**: Manages speakers and session abstracts
- **Attendees**: Manages attendee registrations and profiles
- **Agenda**: Manages the conference schedule and session assignments
- **Merchandise**: Handles merchandise sales and inventory
- **Locations**: Manages venue details and room assignments
- **Sponsors**: Manages sponsor information and interactions
- **Catering**: Manages integration with catering services for the conference

## Integration Points
- **Sales Team System**: Integration via an anti-corruption layer to register attendees from external sales data

```
io.arrogantprogrammer.cfp
  ├── domain              # Core domain model
  │   ├── aggregates      # Aggregate roots (Speaker)
  │   ├── events          # Domain events (
  │   ├── repositories    # Repository interfaces
  │   ├── services        # Domain services
  │   └── valueobjects    # Value objects (SessionAbstract)
  ├── application         # Application services
  └── infrastructure      # Technical implementations
      ├── persistence     # JPA repositories and entities
      └── rest            # REST resources
```

## Key Components

### Domain Layer

- **Aggregates**: `Speaker` and `ConferenceSession` represent the core domain concepts
- **Value Objects**: `SessionAbstract` encapsulates validation and behavior for session abstracts
- **Domain Events**: Events like `SpeakerRegistrationEvent`, `SpeakerUpdatedEvent`, and `AbstractSubmittedEvent`
- **Repository Interfaces**: Define persistence contracts without implementation details

### Application Layer

- **Application Services**: `SpeakerApplicationService` and `ConferenceSessionApplicationService` orchestrate use cases
- **Command/Result Objects**: Encapsulate operations like `SubmitAbstractCommand`

### Infrastructure Layer

- ** Repositories**: Implement domain repository interfaces using Hibernate Panache
- **REST Resources**: Expose domain functionality through REST endpoints using JAX-RS
- **Persistence Entities**: Map domain objects to database structures

## Status

Designed
