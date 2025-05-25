# Architectural Decision Record: CFP Package Refactoring

## Context

The Call for Papers (CFP) module is a critical component of our conference management system, handling the submission and review of session proposals. The original implementation lacked clear separation of concerns and did not follow domain-driven design principles, making it difficult to maintain and extend.

## Decision

We refactored the CFP package to align with Domain-Driven Design (DDD) and Hexagonal Architecture principles as outlined in our ARCHITECTURAL_APPROACH.md document. The refactoring focused on:

1. Establishing clear boundaries between domain, application, and infrastructure layers
2. Implementing proper domain aggregates and value objects
3. Defining repository interfaces in the domain layer
4. Creating application services for orchestrating business logic
5. Implementing infrastructure adapters for persistence and REST APIs

## Structure

The refactored package follows this structure:

```
io.arrogantprogrammer.cfp
  ├── api                 # DTOs and API models
  │   └── dto             # Data Transfer Objects
  ├── domain              # Core domain model
  │   ├── aggregates      # Aggregate roots (Speaker, ConferenceSession)
  │   ├── events          # Domain events
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
- **Command/Result Objects**: Encapsulate operations like `SubmitAbstractCommand` and `SpeakerRegistrationResult`

### Infrastructure Layer

- **JPA Repositories**: Implement domain repository interfaces using JPA/Panache
- **REST Resources**: Expose domain functionality through REST endpoints
- **Persistence Entities**: Map domain objects to database structures

## Benefits

1. **Improved Maintainability**: Clear separation of concerns makes the code easier to understand and modify
2. **Better Testability**: Domain logic can be tested independently of infrastructure
3. **Enhanced Flexibility**: Infrastructure implementations can be changed without affecting domain logic
4. **Clearer Domain Model**: The code now better reflects the business domain
5. **Reduced Coupling**: Dependencies point inward toward the domain core

## Consequences

1. **Increased Complexity**: More classes and interfaces to manage
2. **Learning Curve**: Developers need to understand DDD and Hexagonal Architecture principles
3. **Mapping Overhead**: Need to map between domain objects, DTOs, and persistence entities

## Status

Implemented
