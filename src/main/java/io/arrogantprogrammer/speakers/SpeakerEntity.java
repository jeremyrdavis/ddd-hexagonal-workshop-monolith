package io.arrogantprogrammer.speakers;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "speakers")
public class SpeakerEntity extends PanacheEntity {
    
    @NotBlank(message = "Name is required")
    public String name;
    
    public String title;
    
    public String company;
    
    @Size(max = 500, message = "Bio cannot exceed 500 characters")
    @Column(length = 500)
    public String bio;
    
    public String headshot;
    
    @JsonIgnore
    @OneToMany(mappedBy = "speakerEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<SocialMedia> socialMedia = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpeakerEntity speakerEntity = (SpeakerEntity) o;
        return id != null && id.equals(speakerEntity.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
} 