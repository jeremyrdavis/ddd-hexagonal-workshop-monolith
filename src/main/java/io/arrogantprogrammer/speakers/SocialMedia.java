package io.arrogantprogrammer.speakers;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "social_media")
public class SocialMedia extends PanacheEntity {
    
    @ManyToOne
    public Speaker speaker;
    
    @NotBlank(message = "Platform is required")
    public String platform;
    
    @NotBlank(message = "Handle is required")
    public String handle;
} 