# Value Objects in Domain-Driven Design

This document explains the concept of Value Objects in Domain-Driven Design (DDD) and provides guidelines for implementing them in the Conference Management application.

## What are Value Objects?

Value Objects are one of the building blocks of Domain-Driven Design. They are objects that:

1. **Have no identity**: They are defined by their attributes, not by an identifier.
2. **Are immutable**: Once created, they cannot be changed.
3. **Are equality-comparable**: Two value objects with the same attributes are considered equal.
4. **Encapsulate domain rules**: They contain validation and business rules related to their concept.

Examples of Value Objects include Email, Name, Address, Money, Date Range, etc.

## Benefits of Value Objects

Implementing Value Objects in your domain model provides several benefits:

1. **Improved domain expressiveness**: Value Objects make your code more expressive and closer to the domain language.
2. **Encapsulation of validation rules**: Validation logic is centralized in the Value Object.
3. **Prevention of invalid states**: Value Objects ensure that invalid states cannot exist.
4. **Reduced primitive obsession**: Replaces primitive types with domain-specific types.
5. **Improved testability**: Value Objects are easy to test in isolation.
6. **Self-documentation**: Value Objects make the code more self-documenting.
7. **Reusability**: Value Objects can be reused across different parts of the application.

## Implementation Guidelines

When implementing Value Objects, follow these guidelines:

1. **Make them immutable**: Use final fields and provide no setters.
2. **Validate in the constructor**: Ensure all validation happens during object creation.
3. **Override equals() and hashCode()**: Based on the attributes, not identity.
4. **Override toString()**: For debugging and logging purposes.
5. **Provide factory methods**: For common creation patterns.
6. **Add domain-specific methods**: Methods that express domain concepts.
7. **Use JPA @Embeddable**: For persistence in entities.

## Examples in the Conference Management Application

### Email Value Object

The `Email` value object encapsulates the concept of an email address, including validation and domain-specific behavior:

```java
@Embeddable
public class Email {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    
    private String value;
    
    protected Email() { } // Required by JPA
    
    public Email(String email) {
        validate(email);
        this.value = email;
    }
    
    private void validate(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
    }
    
    public String getValue() {
        return value;
    }
    
    public String getDomain() {
        return value.substring(value.indexOf('@') + 1);
    }
    
    // equals, hashCode, toString implementations
}
```

### Name Value Object

The `Name` value object represents a person's name with first and last name components:

```java
@Embeddable
public class Name {
    private String firstName;
    private String lastName;
    
    protected Name() { } // Required by JPA
    
    public Name(String firstName, String lastName) {
        validate(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public static Name fromFullName(String fullName) {
        // Implementation details
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    // equals, hashCode, toString implementations
}
```

## Integration with Entities

Value Objects can be integrated with entities using the `@Embedded` annotation:

```java
@Entity
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    
    @Embedded
    private Name name;
    
    @Embedded
    private Email email;
    
    // Constructors, getters, etc.
}
```

## Potential Value Objects in the Conference Management Application

Based on the current codebase, the following concepts could be implemented as Value Objects:

1. **Email**: For attendee and speaker email addresses
2. **Name**: For attendee and speaker names
3. **Company**: For attendee and speaker company information
4. **TShirtSize**: For attendee t-shirt preferences
5. **DietaryPreference**: For attendee dietary requirements
6. **SocialMediaHandle**: For social media information
7. **Address**: For venue and location addresses
8. **TimeSlot**: For session time slots
9. **Money**: For pricing and financial information
10. **PhoneNumber**: For contact information

## Migration Strategy

When migrating from primitive types to Value Objects:

1. Create the Value Object class
2. Update entity classes to use the Value Object
3. Update repositories and queries if necessary
4. Update DTOs and mappers
5. Update service methods to handle the Value Object
6. Update tests to use the Value Object

## Conclusion

Value Objects are a powerful tool in Domain-Driven Design that can significantly improve the quality and expressiveness of your code. By implementing Value Objects for concepts like Email, Name, etc., the Conference Management application will benefit from improved domain modeling, better encapsulation, and reduced bugs related to invalid states.