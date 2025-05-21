package io.arrogantprogrammer.cfp;

import io.arrogantprogrammer.domain.valueobjects.Email;
import io.arrogantprogrammer.domain.valueobjects.Name;
import jakarta.persistence.*;
import java.util.Objects;

/**
 * Speaker entity that represents a speaker in the call for papers.
 * This is a Domain-Driven Design entity.
 */
@Entity
@Table(name = "cfp_speakers")
public class Speaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Embedded
    private Name name;
    
    @Embedded
    private Email email;
    
    private String bio;
    
    private String company;
    
    private String title;
    
    private String photoUrl;
    
    /**
     * Required by JPA
     */
    protected Speaker() {
    }
    
    /**
     * Creates a new Speaker instance.
     * 
     * @param name the speaker's name
     * @param email the speaker's email
     * @param bio the speaker's bio
     * @param company the speaker's company
     * @param title the speaker's job title
     * @param photoUrl the URL to the speaker's photo
     */
    public Speaker(Name name, Email email, String bio, String company, String title, String photoUrl) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.bio = bio;
        this.company = company;
        this.title = title;
        this.photoUrl = photoUrl;
    }
    
    /**
     * Factory method to create a Speaker from string values.
     * 
     * @param firstName the speaker's first name
     * @param lastName the speaker's last name
     * @param email the speaker's email address
     * @param bio the speaker's bio
     * @param company the speaker's company
     * @param title the speaker's job title
     * @param photoUrl the URL to the speaker's photo
     * @return a new Speaker instance
     */
    public static Speaker create(Name name, Email email,
                                String bio, String company, String title, String photoUrl) {
        return new Speaker(
            new Name(name.getFirstName(), name.getLastName()),
            email,
            bio,
            company,
            title,
            photoUrl
        );
    }
    
    /**
     * Gets the speaker's ID.
     * 
     * @return the ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Gets the speaker's name.
     * 
     * @return the name value object
     */
    public Name getName() {
        return name;
    }
    
    /**
     * Gets the speaker's email.
     * 
     * @return the email value object
     */
    public Email getEmail() {
        return email;
    }
    
    /**
     * Gets the speaker's bio.
     * 
     * @return the bio
     */
    public String getBio() {
        return bio;
    }
    
    /**
     * Gets the speaker's company.
     * 
     * @return the company
     */
    public String getCompany() {
        return company;
    }
    
    /**
     * Gets the speaker's job title.
     * 
     * @return the job title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Gets the URL to the speaker's photo.
     * 
     * @return the photo URL
     */
    public String getPhotoUrl() {
        return photoUrl;
    }
    
    /**
     * Updates the speaker's email.
     * 
     * @param email the new email
     */
    public void updateEmail(Email email) {
        this.email = Objects.requireNonNull(email, "Email cannot be null");
    }
    
    /**
     * Updates the speaker's name.
     * 
     * @param name the new name
     */
    public void updateName(Name name) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
    }
    
    /**
     * Updates the speaker's bio.
     * 
     * @param bio the new bio
     */
    public void updateBio(String bio) {
        this.bio = bio;
    }
    
    /**
     * Updates the speaker's company.
     * 
     * @param company the new company
     */
    public void updateCompany(String company) {
        this.company = company;
    }
    
    /**
     * Updates the speaker's job title.
     * 
     * @param title the new job title
     */
    public void updateTitle(String title) {
        this.title = title;
    }
    
    /**
     * Updates the URL to the speaker's photo.
     * 
     * @param photoUrl the new photo URL
     */
    public void updatePhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speaker speaker = (Speaker) o;
        return Objects.equals(id, speaker.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Speaker{" +
                "id=" + id +
                ", name=" + name +
                ", email=" + email +
                ", company='" + company + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}