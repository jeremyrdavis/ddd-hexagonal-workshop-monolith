package io.arrogantprogrammer.cfp;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for Speaker.
 */
public class SpeakerDTO {
    public Long id;
    
    @NotBlank(message = "First name is required")
    public String firstName;
    
    @NotBlank(message = "Last name is required")
    public String lastName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    public String email;
    
    @Size(max = 500, message = "Bio cannot exceed 500 characters")
    public String bio;
    
    public String company;
    
    public String title;
    
    public String photoUrl;
    
    public SpeakerDTO() {
    }
    
    public SpeakerDTO(Long id, String firstName, String lastName, String email, 
                    String bio, String company, String title, String photoUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bio = bio;
        this.company = company;
        this.title = title;
        this.photoUrl = photoUrl;
    }
}