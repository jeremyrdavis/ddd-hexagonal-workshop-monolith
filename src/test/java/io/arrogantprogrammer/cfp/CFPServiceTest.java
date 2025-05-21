package io.arrogantprogrammer.cfp;

import io.arrogantprogrammer.domain.valueobjects.Email;
import io.arrogantprogrammer.domain.valueobjects.Name;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@QuarkusTest
public class CFPServiceTest {

    @Inject
    CFPService cfpService;

    @InjectMock
    SpeakerRepository speakerRepository;

    @InjectMock
    ConferenceSessionRepository conferenceSessionRepository;

    @Test
    void testRegisterSpeaker() {
        SpeakerDTO speakerDTO = new SpeakerDTO(
                new Name("Bilbo", "Baggins"), // lastName
                new Email("bilbo@theshire.com"), // email
                "A respectable hobbit of the Shire, known for unexpected adventures and a remarkable journey to the Lonely Mountain.", // bio
                "Test Company", // company
                "Developer", // title
                "https://example.com/photo.jpg" // photoUrl
        );
        SpeakerDTO result = cfpService.registerSpeaker(speakerDTO);
        assertNotNull(result);
        assertEquals(speakerDTO.name(), result.name());
        assertEquals(speakerDTO.email(), result.email());
        assertEquals(speakerDTO.bio(), result.bio());
        assertEquals(speakerDTO.company(), result.company());
        assertEquals(speakerDTO.title(), result.title());
        assertEquals(speakerDTO.photoUrl(), result.photoUrl());
        assertEquals(speakerDTO.name(), result.name());
        assertEquals(speakerDTO.email(), result.email());
        verify(speakerRepository, times(1)).persist(any(Speaker.class));
    }

    @Test
    void testGetAllConferenceSessions() {
        Mockito.when(conferenceSessionRepository.streamAll()).thenReturn(stubConferenceSessionList().stream());
        List<ConferenceSessionDTO> conferenceSessions = cfpService.getAllConferenceSessions();
        assertNotNull(conferenceSessions);
        assertEquals(5, conferenceSessions.size());
    }

    @Test
    void testGetSession() {
        // Create a mock repository
        ConferenceSessionRepository mockRepo = Mockito.mock(ConferenceSessionRepository.class);

        // Create a test session
        ConferenceSession testSession = new ConferenceSession(
                new SessionAbstract(
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
        var result = cfpService.getSession(1L);

        // Verify the result
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals("Test Session", result.get().title());
        assertEquals(ConferenceSession.SessionType.TALK, result.get().sessionType());
        assertEquals(ConferenceSession.SessionLevel.BEGINNER, result.get().sessionLevel());
    }

    @Test
    void testAddSpeakerToSession() {
        // Create mock repositories
        ConferenceSessionRepository mockSessionRepo = Mockito.mock(ConferenceSessionRepository.class);
        SpeakerRepository mockSpeakerRepo = Mockito.mock(SpeakerRepository.class);

        // Create a test session and speaker
        ConferenceSession testSession = new ConferenceSession(
                new SessionAbstract(
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

        Speaker testSpeaker = Speaker.create(
                new Name("Test", "Speaker"),
                new Email("test@example.com"),
                "Test Bio",
                "Test Company",
                "Test Title",
                "https://example.com/photo.jpg"
        );

        // Set up the mocks
        Mockito.when(mockSessionRepo.findById(1L)).thenReturn(testSession);
        Mockito.when(mockSpeakerRepo.findById(1L)).thenReturn(testSpeaker);

        // Install the mocks
        QuarkusMock.installMockForType(mockSessionRepo, ConferenceSessionRepository.class);
        QuarkusMock.installMockForType(mockSpeakerRepo, SpeakerRepository.class);

        // Call the method being tested
        var result = cfpService.addSpeakerToSession(1L, 1L);

        // Verify the result
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().speakers().size());
        assertEquals("Test", result.get().speakers().get(0).name().getFirstName());

        // Verify the session was persisted
        Mockito.verify(mockSessionRepo, times(1)).persist(any(ConferenceSession.class));
    }

    @Test
    void testRemoveSpeakerFromSession() {
        // Create mock repositories
        ConferenceSessionRepository mockSessionRepo = Mockito.mock(ConferenceSessionRepository.class);
        SpeakerRepository mockSpeakerRepo = Mockito.mock(SpeakerRepository.class);

        // Create a test session and speaker
        ConferenceSession testSession = new ConferenceSession(
                new SessionAbstract(
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

        Speaker testSpeaker = Speaker.create(
                new Name("Test", "Speaker"),
                new Email("test@example.com"),
                "Test Bio",
                "Test Company",
                "Test Title",
                "https://example.com/photo.jpg"
        );

        // Add the speaker to the session
        testSession.addSpeaker(testSpeaker);

        // Set up the mocks
        Mockito.when(mockSessionRepo.findById(1L)).thenReturn(testSession);
        Mockito.when(mockSpeakerRepo.findById(1L)).thenReturn(testSpeaker);

        // Install the mocks
        QuarkusMock.installMockForType(mockSessionRepo, ConferenceSessionRepository.class);
        QuarkusMock.installMockForType(mockSpeakerRepo, SpeakerRepository.class);

        // Call the method being tested
        var result = cfpService.removeSpeakerFromSession(1L, 1L);

        // Verify the result
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(0, result.get().speakers().size());

        // Verify the session was persisted
        Mockito.verify(mockSessionRepo, times(1)).persist(any(ConferenceSession.class));
    }

    @Test
    void testAcceptSession() {
        // Create a mock repository
        ConferenceSessionRepository mockRepo = Mockito.mock(ConferenceSessionRepository.class);

        // Create a test session
        ConferenceSession testSession = new ConferenceSession(
                new SessionAbstract(
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

        // Set up the mock
        Mockito.when(mockRepo.findById(1L)).thenReturn(testSession);

        // Install the mock
        QuarkusMock.installMockForType(mockRepo, ConferenceSessionRepository.class);

        // Call the method being tested
        var result = cfpService.acceptSession(1L);

        // Verify the result
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(ConferenceSession.SessionStatus.ACCEPTED.name(), result.get().status());

        // Verify the session was persisted
        Mockito.verify(mockRepo, times(1)).persist(any(ConferenceSession.class));
    }

    @Test
    void testRejectSession() {
        // Create a mock repository
        ConferenceSessionRepository mockRepo = Mockito.mock(ConferenceSessionRepository.class);

        // Create a test session
        ConferenceSession testSession = new ConferenceSession(
                new SessionAbstract(
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

        // Set up the mock
        Mockito.when(mockRepo.findById(1L)).thenReturn(testSession);

        // Install the mock
        QuarkusMock.installMockForType(mockRepo, ConferenceSessionRepository.class);

        // Call the method being tested
        var result = cfpService.rejectSession(1L);

        // Verify the result
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(ConferenceSession.SessionStatus.REJECTED.name(), result.get().status());

        // Verify the session was persisted
        Mockito.verify(mockRepo, times(1)).persist(any(ConferenceSession.class));
    }

    @Test
    void testWithdrawSession() {
        // Create a mock repository
        ConferenceSessionRepository mockRepo = Mockito.mock(ConferenceSessionRepository.class);

        // Create a test session
        ConferenceSession testSession = new ConferenceSession(
                new SessionAbstract(
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

        // Set up the mock
        Mockito.when(mockRepo.findById(1L)).thenReturn(testSession);

        // Install the mock
        QuarkusMock.installMockForType(mockRepo, ConferenceSessionRepository.class);

        // Call the method being tested
        var result = cfpService.withdrawSession(1L);

        // Verify the result
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(ConferenceSession.SessionStatus.WITHDRAWN.name(), result.get().status());

        // Verify the session was persisted
        Mockito.verify(mockRepo, times(1)).persist(any(ConferenceSession.class));
    }

    @Test
    void testFindSessionsByStatus() {
        // Create a mock repository
        ConferenceSessionRepository mockRepo = Mockito.mock(ConferenceSessionRepository.class);

        // Create test sessions
        List<ConferenceSession> acceptedSessions = List.of(
            new ConferenceSession(
                new SessionAbstract(
                    "Accepted Session 1",
                    "Summary 1",
                    "Outline 1",
                    "Learning Objectives 1",
                    "Target Audience 1",
                    "Prerequisites 1"
                ),
                ConferenceSession.SessionType.TALK,
                ConferenceSession.SessionLevel.BEGINNER,
                Duration.ofHours(1)
            ),
            new ConferenceSession(
                new SessionAbstract(
                    "Accepted Session 2",
                    "Summary 2",
                    "Outline 2",
                    "Learning Objectives 2",
                    "Target Audience 2",
                    "Prerequisites 2"
                ),
                ConferenceSession.SessionType.WORKSHOP,
                ConferenceSession.SessionLevel.INTERMEDIATE,
                Duration.ofHours(2)
            )
        );

        // Set the status of the sessions to ACCEPTED
        acceptedSessions.forEach(session -> session.accept());

        // Set up the mock
        Mockito.when(mockRepo.findByStatus(ConferenceSession.SessionStatus.ACCEPTED)).thenReturn(acceptedSessions);

        // Install the mock
        QuarkusMock.installMockForType(mockRepo, ConferenceSessionRepository.class);

        // Call the method being tested
        var result = cfpService.findSessionsByStatus(ConferenceSession.SessionStatus.ACCEPTED);

        // Verify the result
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Accepted Session 1", result.get(0).title());
        assertEquals("Accepted Session 2", result.get(1).title());
        assertEquals(ConferenceSession.SessionStatus.ACCEPTED.name(), result.get(0).status());
        assertEquals(ConferenceSession.SessionStatus.ACCEPTED.name(), result.get(1).status());
    }

    @Test
    void testFindSessionsBySpeaker() {
        // Create mock repositories
        ConferenceSessionRepository mockSessionRepo = Mockito.mock(ConferenceSessionRepository.class);

        // Create a test speaker
        Speaker testSpeaker = Speaker.create(
            new Name("Test", "Speaker"),
            new Email("test@example.com"),
            "Test Bio",
            "Test Company",
            "Test Title",
            "https://example.com/photo.jpg"
        );

        // Create test sessions
        ConferenceSession session1 = new ConferenceSession(
            new SessionAbstract(
                "Speaker Session 1",
                "Summary 1",
                "Outline 1",
                "Learning Objectives 1",
                "Target Audience 1",
                "Prerequisites 1"
            ),
            ConferenceSession.SessionType.TALK,
            ConferenceSession.SessionLevel.BEGINNER,
            Duration.ofHours(1)
        );

        ConferenceSession session2 = new ConferenceSession(
            new SessionAbstract(
                "Speaker Session 2",
                "Summary 2",
                "Outline 2",
                "Learning Objectives 2",
                "Target Audience 2",
                "Prerequisites 2"
            ),
            ConferenceSession.SessionType.WORKSHOP,
            ConferenceSession.SessionLevel.INTERMEDIATE,
            Duration.ofHours(2)
        );

        // Add the speaker to the sessions
        session1.addSpeaker(testSpeaker);
        session2.addSpeaker(testSpeaker);

        // Set up the mock
        Mockito.when(mockSessionRepo.findBySpeakerId(1L)).thenReturn(List.of(session1, session2));

        // Install the mock
        QuarkusMock.installMockForType(mockSessionRepo, ConferenceSessionRepository.class);

        // Call the method being tested
        var result = cfpService.findSessionsBySpeaker(1L);

        // Verify the result
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Speaker Session 1", result.get(0).title());
        assertEquals("Speaker Session 2", result.get(1).title());
    }

    @Test
    void testFindByCompany() {
        // Create a mock repository
        SpeakerRepository mockRepo = Mockito.mock(SpeakerRepository.class);

        // Create test speakers
        List<Speaker> companySpeakers = List.of(
            Speaker.create(
                new Name("John", "Doe"),
                new Email("john@example.com"),
                "John's Bio",
                "Acme Inc",
                "Developer",
                "https://example.com/john.jpg"
            ),
            Speaker.create(
                new Name("Jane", "Smith"),
                new Email("jane@example.com"),
                "Jane's Bio",
                "Acme Inc",
                "Designer",
                "https://example.com/jane.jpg"
            )
        );

        // Set up the mock
        Mockito.when(mockRepo.findByCompany("Acme Inc")).thenReturn(companySpeakers);

        // Install the mock
        QuarkusMock.installMockForType(mockRepo, SpeakerRepository.class);

        // Call the method being tested
        var result = cfpService.findByCompany("Acme Inc");

        // Verify the result
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).name().getFirstName());
        assertEquals("Jane", result.get(1).name().getFirstName());
        assertEquals("Acme Inc", result.get(0).company());
        assertEquals("Acme Inc", result.get(1).company());
    }

    private List<ConferenceSession> stubConferenceSessionList() {
        return List.of(
                new ConferenceSession(
                        new SessionAbstract(
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
                        new SessionAbstract(
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
                        new SessionAbstract(
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
                        new SessionAbstract(
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
                        new SessionAbstract(
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
