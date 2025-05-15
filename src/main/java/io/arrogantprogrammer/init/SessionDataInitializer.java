package io.arrogantprogrammer.init;

import io.arrogantprogrammer.sessions.Session;
import io.arrogantprogrammer.sessions.SessionRepository;
import io.arrogantprogrammer.speakers.SpeakerRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class SessionDataInitializer implements DataInitializer {

    @Inject
    SessionRepository sessionRepository;

    @Inject
    SpeakerRepository speakerRepository;

    @Override
    @Transactional
    public void initialize() {
        if (sessionRepository.count() > 0) {
            Log.info("Sessions already initialized, skipping...");
            return;
        }

        // Get all speakers for assignment
        List<Long> speakerIds = speakerRepository.listAll().stream()
            .map(speaker -> speaker.id)
            .toList();

        if (speakerIds.isEmpty()) {
            Log.warn("No speakers found for session initialization");
            return;
        }

        LocalDateTime startTime = LocalDateTime.now().plusDays(1).withHour(9).withMinute(0);
        List<Session> sessions = Arrays.asList(
            createSession("Keynote: The Future of Software Development", 
                "Join us for an inspiring keynote on the future of software development, exploring emerging trends and technologies.",
                startTime, startTime.plusHours(1), "Main Hall",
                Arrays.asList(speakerIds.get(0), speakerIds.get(1))),

            createSession("Microservices Architecture: Best Practices", 
                "Learn about designing and implementing microservices architectures at scale.",
                startTime.plusHours(1).plusMinutes(30), startTime.plusHours(2).plusMinutes(30), "Room A",
                Arrays.asList(speakerIds.get(2), speakerIds.get(3))),

            createSession("Coffee Break", 
                "Network with fellow attendees and enjoy refreshments.",
                startTime.plusHours(2).plusMinutes(30), startTime.plusHours(3), "Lobby"),

            createSession("Cloud-Native Development with Kubernetes", 
                "Deep dive into building and deploying cloud-native applications using Kubernetes.",
                startTime.plusHours(3), startTime.plusHours(4), "Room B",
                Arrays.asList(speakerIds.get(4), speakerIds.get(5))),

            createSession("Lunch Break", 
                "Enjoy lunch and networking opportunities.",
                startTime.plusHours(4), startTime.plusHours(5), "Dining Hall"),

            createSession("AI and Machine Learning in Practice", 
                "Practical applications of AI and machine learning in modern software development.",
                startTime.plusHours(5), startTime.plusHours(6), "Room C",
                Arrays.asList(speakerIds.get(6), speakerIds.get(7))),

            createSession("DevOps and CI/CD Pipelines", 
                "Building efficient CI/CD pipelines for modern development workflows.",
                startTime.plusHours(6).plusMinutes(30), startTime.plusHours(7).plusMinutes(30), "Room A",
                Arrays.asList(speakerIds.get(8), speakerIds.get(9))),

            createSession("Security Best Practices", 
                "Essential security practices for modern applications.",
                startTime.plusHours(6).plusMinutes(30), startTime.plusHours(7).plusMinutes(30), "Room B",
                Arrays.asList(speakerIds.get(10), speakerIds.get(11))),

            createSession("Frontend Development Trends", 
                "Exploring the latest trends in frontend development and frameworks.",
                startTime.plusHours(8), startTime.plusHours(9), "Room C",
                Arrays.asList(speakerIds.get(12), speakerIds.get(13))),

            createSession("Database Design and Optimization", 
                "Best practices for database design and performance optimization.",
                startTime.plusHours(8), startTime.plusHours(9), "Room D",
                Arrays.asList(speakerIds.get(14), speakerIds.get(15))),

            createSession("Blockchain Development", 
                "Introduction to blockchain development and smart contracts.",
                startTime.plusHours(9).plusMinutes(30), startTime.plusHours(10).plusMinutes(30), "Room A",
                Arrays.asList(speakerIds.get(16), speakerIds.get(17))),

            createSession("Mobile App Development", 
                "Cross-platform mobile development strategies and tools.",
                startTime.plusHours(9).plusMinutes(30), startTime.plusHours(10).plusMinutes(30), "Room B",
                Arrays.asList(speakerIds.get(18), speakerIds.get(19))),

            createSession("API Design and Documentation", 
                "Best practices for designing and documenting RESTful APIs.",
                startTime.plusHours(11), startTime.plusHours(12), "Room C",
                Arrays.asList(speakerIds.get(20), speakerIds.get(21))),

            createSession("Testing Strategies", 
                "Comprehensive testing strategies for modern applications.",
                startTime.plusHours(11), startTime.plusHours(12), "Room D",
                Arrays.asList(speakerIds.get(22), speakerIds.get(23))),

            createSession("Closing Remarks", 
                "Conference wrap-up and key takeaways.",
                startTime.plusHours(12).plusMinutes(30), startTime.plusHours(13), "Main Hall",
                Arrays.asList(speakerIds.get(0)))
        );

        sessions.forEach(sessionRepository::persist);
        Log.info("Initialized " + sessions.size() + " sessions");
    }

    @Override
    public int getOrder() {
        return 2;
    }

    private Session createSession(String title, String description, 
                                LocalDateTime startTime, LocalDateTime endTime, 
                                String room, List<Long> speakerIds) {
        Session session = new Session();
        session.title = title;
        session.description = description;
        session.startTime = startTime;
        session.endTime = endTime;
        session.room = room;
        session.speakers = speakerIds.stream()
            .map(id -> speakerRepository.findById(id))
            .toList();
        return session;
    }

    private Session createSession(String title, String description, 
                                LocalDateTime startTime, LocalDateTime endTime, 
                                String room) {
        return createSession(title, description, startTime, endTime, room, List.of());
    }
} 