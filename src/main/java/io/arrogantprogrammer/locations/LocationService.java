package io.arrogantprogrammer.locations;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class LocationService {

    @Inject
    LocationRepository locationRepository;

    @Transactional
    public Location createLocation(Location location) {
        locationRepository.persist(location);
        return location;
    }

    public List<Location> getAllLocations() {
        return locationRepository.listAll();
    }

    public Location getLocationById(Long id) {
        return locationRepository.findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException("Location not found", Response.Status.NOT_FOUND));
    }

    @Transactional
    public Location updateLocation(Long id, Location location) {
        Location existingLocation = getLocationById(id);
        existingLocation.name = location.name;
        existingLocation.type = location.type;
        existingLocation.address = location.address;
        existingLocation.description = location.description;
        existingLocation.mapUrl = location.mapUrl;
        return existingLocation;
    }

    @Transactional
    public void deleteLocation(Long id) {
        Location location = getLocationById(id);
        locationRepository.delete(location);
    }
} 