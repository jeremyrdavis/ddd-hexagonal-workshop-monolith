package io.arrogantprogrammer.domain.valueobjects;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Objects;

/**
 * Example entity that demonstrates how to use value objects.
 * This is a simple Person entity that uses the Name and Email value objects.
 */
@Entity
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    
    @Embedded
    private Name name;
    
    @Embedded
    private Email email;
    
    protected Person() {
        // Required by JPA
    }
    
    /**
     * Creates a new Person with the given name and email.
     * 
     * @param name the person's name
     * @param email the person's email
     */
    public Person(Name name, Email email) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
    }
    
    /**
     * Factory method to create a Person from string values.
     * 
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @param email the person's email address
     * @return a new Person instance
     */
    public static Person create(String firstName, String lastName, String email) {
        return new Person(
            new Name(firstName, lastName),
            new Email(email)
        );
    }
    
    /**
     * Gets the person's ID.
     * 
     * @return the ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Gets the person's name.
     * 
     * @return the name value object
     */
    public Name getName() {
        return name;
    }
    
    /**
     * Gets the person's email.
     * 
     * @return the email value object
     */
    public Email getEmail() {
        return email;
    }
    
    /**
     * Updates the person's email.
     * 
     * @param email the new email
     */
    public void updateEmail(Email email) {
        this.email = Objects.requireNonNull(email, "Email cannot be null");
    }
    
    /**
     * Updates the person's name.
     * 
     * @param name the new name
     */
    public void updateName(Name name) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name=" + name +
                ", email=" + email +
                '}';
    }
}