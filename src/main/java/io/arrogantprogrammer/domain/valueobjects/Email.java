package io.arrogantprogrammer.domain.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Email value object that encapsulates email validation and behavior.
 * This is an example of a Domain-Driven Design value object.
 */
@Embeddable
public class Email {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    
    private String value;
    
    /**
     * Required by JPA
     */
    protected Email() {
    }
    
    /**
     * Creates a new Email instance after validating the input.
     * 
     * @param email the email address string
     * @throws IllegalArgumentException if the email is invalid
     */
    public Email(String email) {
        validate(email);
        this.value = email;
    }
    
    /**
     * Validates that the email string is properly formatted.
     * 
     * @param email the email address to validate
     * @throws IllegalArgumentException if the email is invalid
     */
    private void validate(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
        
        if (email.length() > 255) {
            throw new IllegalArgumentException("Email is too long (max 255 characters)");
        }
    }
    
    /**
     * Returns the email address as a string.
     * 
     * @return the email address
     */
    public String getValue() {
        return value;
    }
    
    /**
     * Returns the domain part of the email address.
     * 
     * @return the domain part of the email
     */
    public String getDomain() {
        return value.substring(value.indexOf('@') + 1);
    }
    
    /**
     * Returns the local part of the email address (before the @ symbol).
     * 
     * @return the local part of the email
     */
    public String getLocalPart() {
        return value.substring(0, value.indexOf('@'));
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value.toLowerCase(), email.value.toLowerCase());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value.toLowerCase());
    }
    
    @Override
    public String toString() {
        return value;
    }
}