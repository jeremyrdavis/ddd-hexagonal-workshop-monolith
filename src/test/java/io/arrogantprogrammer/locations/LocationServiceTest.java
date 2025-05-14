package io.arrogantprogrammer.locations;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
public class LocationServiceTest {

    @InjectMocks
    LocationService locationService;

    @Mock
    LocationRepository locationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLocation() {
        Location location = new Location();
        location.name = "Test Venue";
        location.type = Location.LocationType.VENUE;
        location.address = "123 Test St";
        
        doAnswer(invocation -> {
            Location l = invocation.getArgument(0);
            return l;
        }).when(locationRepository).persist(any(Location.class));
        
        Location created = locationService.createLocation(location);
        
        assertNotNull(created);
        assertEquals("Test Venue", created.name);
        assertEquals(Location.LocationType.VENUE, created.type);
        verify(locationRepository).persist(location);
    }

    @Test
    void testGetAllLocations() {
        Location location1 = new Location();
        location1.name = "Venue 1";
        Location location2 = new Location();
        location2.name = "Venue 2";
        List<Location> locations = Arrays.asList(location1, location2);
        
        when(locationRepository.listAll()).thenReturn(locations);
        
        List<Location> result = locationService.getAllLocations();
        
        assertEquals(2, result.size());
        verify(locationRepository).listAll();
    }

    @Test
    void testGetLocationById() {
        Location location = new Location();
        location.id = 1L;
        location.name = "Test Venue";
        
        when(locationRepository.findByIdOptional(1L)).thenReturn(Optional.of(location));
        
        Location result = locationService.getLocationById(1L);
        
        assertNotNull(result);
        assertEquals(1L, result.id);
        assertEquals("Test Venue", result.name);
    }

    @Test
    void testGetLocationByIdNotFound() {
        when(locationRepository.findByIdOptional(999L)).thenReturn(Optional.empty());
        
        assertThrows(WebApplicationException.class, () -> {
            locationService.getLocationById(999L);
        });
    }

    @Test
    void testUpdateLocation() {
        Location existingLocation = new Location();
        existingLocation.id = 1L;
        existingLocation.name = "Old Name";
        existingLocation.type = Location.LocationType.VENUE;
        
        Location updatedLocation = new Location();
        updatedLocation.name = "New Name";
        updatedLocation.type = Location.LocationType.HOTEL;
        
        when(locationRepository.findByIdOptional(1L)).thenReturn(Optional.of(existingLocation));
        
        Location result = locationService.updateLocation(1L, updatedLocation);
        
        assertEquals("New Name", result.name);
        assertEquals(Location.LocationType.HOTEL, result.type);
    }

    @Test
    void testDeleteLocation() {
        Location location = new Location();
        location.id = 1L;
        
        when(locationRepository.findByIdOptional(1L)).thenReturn(Optional.of(location));
        doNothing().when(locationRepository).delete(location);
        
        locationService.deleteLocation(1L);
        
        verify(locationRepository).delete(location);
    }
} 