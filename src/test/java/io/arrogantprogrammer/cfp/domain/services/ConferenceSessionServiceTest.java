package io.arrogantprogrammer.cfp.domain.services;

import io.arrogantprogrammer.cfp.ConferenceSession;
import io.arrogantprogrammer.cfp.ConferenceSessionDTO;
import io.arrogantprogrammer.cfp.ConferenceSessionRepository;
import io.arrogantprogrammer.cfp.persistence.SessionAbstractEntity;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ConferenceSessionServiceTest {

    @Inject
    ConferenceSessionService conferenceSessionService;

    @InjectMock
    ConferenceSessionRepository repository;

    @InjectMock
    ConferenceSessionRepository conferenceSessionRepository;

    @Test
    void testGetAllConferenceSessions() {
        Mockito.when(conferenceSessionRepository.streamAll()).thenReturn(stubConferenceSessionList().stream());
        List<ConferenceSessionDTO> conferenceSessions = conferenceSessionService.getAllSessions();
        assertNotNull(conferenceSessions);
        assertEquals(5, conferenceSessions.size());
    }

    @Test
    void testGetSession() {
        // Create a mock repository
        ConferenceSessionRepository mockRepo = Mockito.mock(ConferenceSessionRepository.class);

        // Create a test session
        ConferenceSession testSession = new ConferenceSession(
                new SessionAbstractEntity(
                        "Test Session",
                        "Test Summary",
                        "Test Outline",
                        "Test Learning Objectives",
                        "Test Target Audience",
                        "Test Prerequisites"
                ),
                ConferenceSession.SessionType.TALK,
                ConferenceSession.SessionLevel.BEGINNER,
                Duration.ofHours(1)
        );

        // Set up the mock to return our test session when findByIdOptional is called with ID 1
        Mockito.when(mockRepo.findByIdOptional(1L)).thenReturn(java.util.Optional.of(testSession));

        // Install the mock
        QuarkusMock.installMockForType(mockRepo, ConferenceSessionRepository.class);

        // Call the method being tested
        var result = conferenceSessionService.getSession(1L);

        // Verify the result
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals("Test Session", result.get().title());
        assertEquals(ConferenceSession.SessionType.TALK, result.get().sessionType());
        assertEquals(ConferenceSession.SessionLevel.BEGINNER, result.get().sessionLevel());
    }

    private List<ConferenceSession> stubConferenceSessionList() {
        return List.of(
                new ConferenceSession(
                        new SessionAbstractEntity(
                                "The Fellowship of the Ring",
                                "An introduction to the journey of the Fellowship.",
                                "Overview of the Fellowship's formation and their quest.",
                                "Understand the origins of the Fellowship and their mission.",
                                "Fans of epic adventures and teamwork.",
                                "Basic knowledge of Middle-earth lore."
                        ),
                        ConferenceSession.SessionType.TALK,
                        ConferenceSession.SessionLevel.BEGINNER,
                        Duration.ofHours(2)
                ),
                new ConferenceSession(
                        new SessionAbstractEntity(
                                "The Two Towers",
                                "Exploring the battles and alliances formed in Middle-earth.",
                                "Detailed analysis of key battles and character arcs.",
                                "Learn about the strategic alliances and conflicts.",
                                "Intermediate-level enthusiasts of Middle-earth.",
                                "Familiarity with the Fellowship's journey."
                        ),
                        ConferenceSession.SessionType.PANEL,
                        ConferenceSession.SessionLevel.INTERMEDIATE,
                        Duration.ofHours(3)
                ),
                new ConferenceSession(
                        new SessionAbstractEntity(
                                "The Return of the King",
                                "The conclusion of the epic journey and the rise of Aragorn.",
                                "Discussion on the final battles and Aragorn's coronation.",
                                "Gain insights into the resolution of the story.",
                                "Advanced fans of Tolkien's works.",
                                "Understanding of the preceding events in the trilogy."
                        ),
                        ConferenceSession.SessionType.KEYNOTE,
                        ConferenceSession.SessionLevel.ADVANCED,
                        Duration.ofHours(1)
                ),
                new ConferenceSession(
                        new SessionAbstractEntity(
                                "The Shire: A Peaceful Haven",
                                "A look into the culture and lifestyle of hobbits.",
                                "Exploration of the Shire's traditions and daily life.",
                                "Appreciate the simplicity and values of hobbit life.",
                                "Beginner-level attendees interested in peaceful societies.",
                                "No prerequisites required."
                        ),
                        ConferenceSession.SessionType.WORKSHOP,
                        ConferenceSession.SessionLevel.BEGINNER,
                        Duration.ofMinutes(90)
                ),
                new ConferenceSession(
                        new SessionAbstractEntity(
                                "The One Ring: A Study in Power",
                                "An analysis of the One Ring and its influence.",
                                "Examination of the Ring's creation, power, and corruption.",
                                "Understand the psychological and societal impact of power.",
                                "Experts in mythology and power dynamics.",
                                "Deep knowledge of Middle-earth's history."
                        ),
                        ConferenceSession.SessionType.LIGHTNING_TALK,
                        ConferenceSession.SessionLevel.EXPERT,
                        Duration.ofMinutes(30)
                )
        );
    }

}
