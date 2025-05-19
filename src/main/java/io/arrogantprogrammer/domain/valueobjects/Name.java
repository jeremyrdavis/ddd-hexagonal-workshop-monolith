package io.arrogantprogrammer.domain.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

/**
 * Name value object that encapsulates name validation and behavior.
 * This is an example of a Domain-Driven Design value object.
 */
@Embeddable
public class Name {
    private String firstName;
    private String lastName;
    
    /**
     * Required by JPA
     */
    protected Name() {
    }
    
    /**
     * Creates a new Name instance after validating the input.
     * 
     * @param firstName the first name
     * @param lastName the last name
     * @throws IllegalArgumentException if the name is invalid
     */
    public Name(String firstName, String lastName) {
        validate(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * Creates a new Name instance from a full name string.
     * 
     * @param fullName the full name in "FirstName LastName" format
     * @return a new Name instance
     * @throws IllegalArgumentException if the name is invalid
     */
    public static Name fromFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        
        String[] parts = fullName.trim().split("\\s+", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Full name must contain both first and last name");
        }
        
        return new Name(parts[0], parts[1]);
    }
    
    /**
     * Validates that the name components are properly formatted.
     * 
     * @param firstName the first name to validate
     * @param lastName the last name to validate
     * @throws IllegalArgumentException if the name is invalid
     */
    private void validate(String firstName, String lastName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        
        if (firstName.length() > 100) {
            throw new IllegalArgumentException("First name is too long (max 100 characters)");
        }
        
        if (lastName.length() > 100) {
            throw new IllegalArgumentException("Last name is too long (max 100 characters)");
        }
    }
    
    /**
     * Returns the first name.
     * 
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Returns the last name.
     * 
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Returns the full name in "FirstName LastName" format.
     * 
     * @return the full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    /**
     * Returns the initials in "F.L." format.
     * 
     * @return the initials
     */
    public String getInitials() {
        return firstName.substring(0, 1) + "." + lastName.substring(0, 1) + ".";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(firstName.toLowerCase(), name.firstName.toLowerCase()) &&
               Objects.equals(lastName.toLowerCase(), name.lastName.toLowerCase());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(firstName.toLowerCase(), lastName.toLowerCase());
    }
    
    @Override
    public String toString() {
        return getFullName();
    }
}