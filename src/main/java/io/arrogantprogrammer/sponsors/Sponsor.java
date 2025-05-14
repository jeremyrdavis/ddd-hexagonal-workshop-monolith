package io.arrogantprogrammer.sponsors;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Sponsor extends PanacheEntity {
    
    @NotBlank
    public String name;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    public SponsorTier tier;
    
    public String logo;
    
    public String website;
    
    public String description;
    
    public enum SponsorTier {
        PLATINUM,
        GOLD,
        SILVER,
        BRONZE
    }
} 