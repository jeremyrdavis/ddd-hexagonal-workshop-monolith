package io.arrogantprogrammer.init;

import io.arrogantprogrammer.locations.Location;
import io.arrogantprogrammer.locations.LocationRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class LocationDataInitializer implements DataInitializer {

    @Inject
    LocationRepository locationRepository;

    @Override
    @Transactional
    public void initialize() {
        if (locationRepository.count() > 0) {
            Log.info("Locations already initialized, skipping...");
            return;
        }

        List<Location> locations = Arrays.asList(
            createLocation("Main Hall", 
                "The main conference hall with a capacity of 500 people. Features state-of-the-art audio-visual equipment and comfortable seating.",
                "123 Conference Center, Main Street, Tech City",
                "https://maps.example.com/main-hall",
                Location.LocationType.VENUE),
                
            createLocation("Room A", 
                "A medium-sized conference room perfect for technical sessions. Equipped with projector and whiteboard.",
                "123 Conference Center, First Floor, Tech City",
                "https://maps.example.com/room-a",
                Location.LocationType.VENUE),
                
            createLocation("Room B", 
                "A medium-sized conference room with flexible seating arrangements. Features video conferencing capabilities.",
                "123 Conference Center, First Floor, Tech City",
                "https://maps.example.com/room-b",
                Location.LocationType.VENUE),
                
            createLocation("Room C", 
                "A smaller conference room ideal for workshops and interactive sessions. Includes movable furniture.",
                "123 Conference Center, Second Floor, Tech City",
                "https://maps.example.com/room-c",
                Location.LocationType.VENUE),
                
            createLocation("Room D", 
                "A smaller conference room with a focus on collaboration. Features multiple whiteboards and power outlets.",
                "123 Conference Center, Second Floor, Tech City",
                "https://maps.example.com/room-d",
                Location.LocationType.VENUE),
                
            createLocation("Lobby", 
                "The main entrance area with comfortable seating and networking spaces. Includes a coffee bar.",
                "123 Conference Center, Ground Floor, Tech City",
                "https://maps.example.com/lobby",
                Location.LocationType.VENUE),
                
            createLocation("Dining Hall", 
                "A spacious dining area serving breakfast, lunch, and refreshments throughout the day.",
                "123 Conference Center, Ground Floor, Tech City",
                "https://maps.example.com/dining-hall",
                Location.LocationType.VENUE),
                
            createLocation("Exhibition Area", 
                "A large space for sponsor booths and technology demonstrations. Features high-speed internet and power stations.",
                "123 Conference Center, Ground Floor, Tech City",
                "https://maps.example.com/exhibition",
                Location.LocationType.VENUE),
                
            createLocation("Quiet Room", 
                "A peaceful space for relaxation, meditation, or focused work. Features comfortable seating and natural lighting.",
                "123 Conference Center, Second Floor, Tech City",
                "https://maps.example.com/quiet-room",
                Location.LocationType.VENUE),
                
            createLocation("Networking Lounge", 
                "A casual space for networking and informal discussions. Includes comfortable seating and refreshments.",
                "123 Conference Center, First Floor, Tech City",
                "https://maps.example.com/networking-lounge",
                Location.LocationType.VENUE)
        );

        locations.forEach(locationRepository::persist);
        Log.info("Initialized " + locations.size() + " locations");
    }

    @Override
    public int getOrder() {
        return 3;
    }

    private Location createLocation(String name, String description, String address, 
                                  String mapUrl, Location.LocationType type) {
        Location location = new Location();
        location.name = name;
        location.description = description;
        location.address = address;
        location.mapUrl = mapUrl;
        location.type = type;
        return location;
    }
} 