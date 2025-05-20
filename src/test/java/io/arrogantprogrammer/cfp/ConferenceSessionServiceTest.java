package io.arrogantprogrammer.cfp;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doAnswer;

@QuarkusTest
public class ConferenceSessionServiceTest {

    @Inject
    ConferenceSessionService conferenceSessionService;

    @InjectMock
    ConferenceSessionRepository sessionRepository;

    @InjectMock
    SpeakerRepository speakerRepository;

    private ConferenceSession session;
    private Speaker speaker;

    @BeforeEach
    void setUp() {
        // Create a session for testing
        SessionAbstract sessionAbstract = new SessionAbstract(
                "Test Session",
                "This is a test session summary",
                "Session outline",
                "Learning objectives",
                "Target audience",
                "Prerequisites"
        );

        session = new ConferenceSession(
                sessionAbstract,
                ConferenceSession.SessionType.TALK,
                ConferenceSession.SessionLevel.INTERMEDIATE,
                Duration.ofMinutes(45)
        );

        // Set an ID for the session
        Mockito.when(sessionRepository.findByIdOptional(1L)).thenReturn(Optional.of(session));

        // Create a speaker for testing
        speaker = Speaker.create(
                "John",
                "Doe",
                "john.doe@example.com",
                "Speaker bio",
                "Example Corp",
                "Developer",
                "https://example.com/photo.jpg"
        );

        // Set an ID for the speaker
        Mockito.when(speakerRepository.findByIdOptional(1L)).thenReturn(Optional.of(speaker));

        // Mock persist method
        doAnswer(invocation -> {
            ConferenceSession s = invocation.getArgument(0);
            return s;
        }).when(sessionRepository).persist(any(ConferenceSession.class));

        // Install mocks
        QuarkusMock.installMockForType(sessionRepository, ConferenceSessionRepository.class);
        QuarkusMock.installMockForType(speakerRepository, SpeakerRepository.class);
    }

    @Test
    void getAllSessions() {
        // Arrange
        List<ConferenceSession> expectedSessions = Arrays.asList(session);
        when(sessionRepository.listAll()).thenReturn(expectedSessions);

        // Act
        List<ConferenceSession> actualSessions = conferenceSessionService.getAllSessions();

        // Assert
        assertEquals(expectedSessions, actualSessions);
        verify(sessionRepository).listAll();
    }

    @Test
    void getSession() {
        // Act
        Optional<ConferenceSession> result = conferenceSessionService.getSession(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(session, result.get());
        verify(sessionRepository).findByIdOptional(1L);
    }

    @Test
    void getSessionNotFound() {
        // Arrange
        when(sessionRepository.findByIdOptional(99L)).thenReturn(Optional.empty());

        // Act
        Optional<ConferenceSession> result = conferenceSessionService.getSession(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(sessionRepository).findByIdOptional(99L);
    }

    @Test
    void createSession() {
        // Act
        ConferenceSession result = conferenceSessionService.createSession(session);

        // Assert
        assertEquals(session, result);
        verify(sessionRepository).persist(session);
    }

    @Test
    void updateSession() {
        // Arrange
        SessionAbstract updatedAbstract = new SessionAbstract(
                "Updated Session",
                "Updated summary",
                "Updated outline",
                "Updated objectives",
                "Updated audience",
                "Updated prerequisites"
        );

        ConferenceSession updatedSession = new ConferenceSession(
                updatedAbstract,
                ConferenceSession.SessionType.WORKSHOP,
                ConferenceSession.SessionLevel.ADVANCED,
                Duration.ofMinutes(90)
        );

        // Act
        Optional<ConferenceSession> result = conferenceSessionService.updateSession(1L, updatedSession);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(updatedSession.getSessionAbstract().getTitle(), result.get().getSessionAbstract().getTitle());
        assertEquals(updatedSession.getSessionType(), result.get().getSessionType());
        assertEquals(updatedSession.getSessionLevel(), result.get().getSessionLevel());
        assertEquals(updatedSession.getDuration(), result.get().getDuration());
        verify(sessionRepository).findByIdOptional(1L);
    }

    @Test
    void updateSessionNotFound() {
        // Arrange
        when(sessionRepository.findByIdOptional(99L)).thenReturn(Optional.empty());

        SessionAbstract updatedAbstract = new SessionAbstract(
                "Updated Session",
                "Updated summary",
                "Updated outline",
                "Updated objectives",
                "Updated audience",
                "Updated prerequisites"
        );

        ConferenceSession updatedSession = new ConferenceSession(
                updatedAbstract,
                ConferenceSession.SessionType.WORKSHOP,
                ConferenceSession.SessionLevel.ADVANCED,
                Duration.ofMinutes(90)
        );

        // Act
        Optional<ConferenceSession> result = conferenceSessionService.updateSession(99L, updatedSession);

        // Assert
        assertFalse(result.isPresent());
        verify(sessionRepository).findByIdOptional(99L);
    }

    @Test
    void deleteSession() {
        // Arrange
        when(sessionRepository.deleteById(1L)).thenReturn(true);

        // Act
        boolean result = conferenceSessionService.deleteSession(1L);

        // Assert
        assertTrue(result);
        verify(sessionRepository).deleteById(1L);
    }

    @Test
    void deleteSessionNotFound() {
        // Arrange
        when(sessionRepository.deleteById(99L)).thenReturn(false);

        // Act
        boolean result = conferenceSessionService.deleteSession(99L);

        // Assert
        assertFalse(result);
        verify(sessionRepository).deleteById(99L);
    }

    @Test
    void addSpeakerToSession() {
        // Act
        Optional<ConferenceSession> result = conferenceSessionService.addSpeakerToSession(1L, 1L);

        // Assert
        assertTrue(result.isPresent());
        verify(sessionRepository).findByIdOptional(1L);
        verify(speakerRepository).findByIdOptional(1L);
    }

    @Test
    void addSpeakerToSessionNotFound() {
        // Arrange
        when(sessionRepository.findByIdOptional(99L)).thenReturn(Optional.empty());

        // Act
        Optional<ConferenceSession> result = conferenceSessionService.addSpeakerToSession(99L, 1L);

        // Assert
        assertFalse(result.isPresent());
        verify(sessionRepository).findByIdOptional(99L);
    }

    @Test
    void removeSpeakerFromSession() {
        // Arrange
        session.addSpeaker(speaker);

        // Act
        Optional<ConferenceSession> result = conferenceSessionService.removeSpeakerFromSession(1L, 1L);

        // Assert
        assertTrue(result.isPresent());
        verify(sessionRepository).findByIdOptional(1L);
        verify(speakerRepository).findByIdOptional(1L);
    }

    @Test
    void acceptSession() {
        // Act
        Optional<ConferenceSession> result = conferenceSessionService.acceptSession(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(ConferenceSession.SessionStatus.ACCEPTED, result.get().getStatus());
        verify(sessionRepository).findByIdOptional(1L);
    }

    @Test
    void rejectSession() {
        // Act
        Optional<ConferenceSession> result = conferenceSessionService.rejectSession(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(ConferenceSession.SessionStatus.REJECTED, result.get().getStatus());
        verify(sessionRepository).findByIdOptional(1L);
    }

    @Test
    void withdrawSession() {
        // Act
        Optional<ConferenceSession> result = conferenceSessionService.withdrawSession(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(ConferenceSession.SessionStatus.WITHDRAWN, result.get().getStatus());
        verify(sessionRepository).findByIdOptional(1L);
    }

    @Test
    void findSessionsBySpeaker() {
        // Arrange
        List<ConferenceSession> expectedSessions = Arrays.asList(session);
        when(sessionRepository.findBySpeakerId(1L)).thenReturn(expectedSessions);

        // Act
        List<ConferenceSession> result = conferenceSessionService.findSessionsBySpeaker(1L);

        // Assert
        assertEquals(expectedSessions, result);
        verify(sessionRepository).findBySpeakerId(1L);
    }

    @Test
    void findSessionsByStatus() {
        // Arrange
        List<ConferenceSession> expectedSessions = Arrays.asList(session);
        when(sessionRepository.findByStatus(ConferenceSession.SessionStatus.SUBMITTED)).thenReturn(expectedSessions);

        // Act
        List<ConferenceSession> result = conferenceSessionService.findSessionsByStatus(ConferenceSession.SessionStatus.SUBMITTED);

        // Assert
        assertEquals(expectedSessions, result);
        verify(sessionRepository).findByStatus(ConferenceSession.SessionStatus.SUBMITTED);
    }
}
