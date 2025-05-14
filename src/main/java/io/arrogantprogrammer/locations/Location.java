package io.arrogantprogrammer.locations;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Location extends PanacheEntity {
    
    @NotBlank
    public String name;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    public LocationType type;
    
    @NotBlank
    public String address;
    
    public String description;
    
    public String mapUrl;
    
    public enum LocationType {
        VENUE,
        HOTEL,
        RESTAURANT
    }
} 