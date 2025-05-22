package io.arrogantprogrammer.speakers;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "speakers")
public class Speaker {

    @Id @GeneratedValue
    public Long id;

    @NotBlank(message = "Name is required")
    public String name;

    public String email;
    
    public String title;
    
    public String company;
    
    @Size(max = 500, message = "Bio cannot exceed 500 characters")
    @Column(length = 500)
    public String bio;
    
    public String headshot;
    
    @JsonIgnore
    @OneToMany(mappedBy = "speaker", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<SocialMedia> socialMedia = new ArrayList<>();

    public Speaker() {
    }

    public Speaker(String name, String email, String title, String company, String bio, String headshot, List<SocialMedia> socialMedia) {
        this.name = name;
        this.email = email;
        this.title = title;
        this.company = company;
        this.bio = bio;
        this.headshot = headshot;
        this.socialMedia = socialMedia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speaker speaker = (Speaker) o;
        return id != null && id.equals(speaker.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }

    public List<SocialMedia> getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(List<SocialMedia> socialMedia) {
        this.socialMedia = socialMedia;
    }


}