package io.arrogantprogrammer.speakers;

import io.arrogantprogrammer.sessions.Session;
import io.arrogantprogrammer.sessions.SessionRepository;
import io.arrogantprogrammer.sessions.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SessionServiceTest {
    @Mock
    SessionRepository sessionRepository;
    @Mock
    SpeakerRepository speakerRepository;
    @InjectMocks
    SessionService sessionService;

    Speaker speaker;
    Session session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        speaker = new Speaker();
        speaker.id = 1L;
        speaker.name = "Alice";
        session = new Session();
        session.id = 10L;
        session.title = "Session 1";
        session.speakerEntities = new ArrayList<>();
    }

    @Test
    void testCreateAndGetSession() {
        doAnswer(invocation -> {
            Session s = invocation.getArgument(0);
            s.id = 100L;
            return null;
        }).when(sessionRepository).persist(any(Session.class));
        Session created = sessionService.createSession(session);
        assertNotNull(created);
        assertEquals("Session 1", created.title);
    }

    @Test
    void testUpdateSession() {
        when(sessionRepository.findByIdOptional(10L)).thenReturn(Optional.of(session));
        Session update = new Session();
        update.title = "Updated";
        update.description = "desc";
        update.startTime = LocalDateTime.now();
        update.endTime = LocalDateTime.now().plusHours(1);
        update.room = "Room 1";
        Optional<Session> updated = sessionService.updateSession(10L, update);
        assertTrue(updated.isPresent());
        assertEquals("Updated", updated.get().title);
    }

    @Test
    void testDeleteSession() {
        when(sessionRepository.deleteById(10L)).thenReturn(true);
        assertTrue(sessionService.deleteSession(10L));
    }

    @Test
    void testAddSpeakerToSession() {
        when(sessionRepository.findByIdOptional(10L)).thenReturn(Optional.of(session));
        when(speakerRepository.findByIdOptional(1L)).thenReturn(Optional.of(speaker));
        Optional<Session> result = sessionService.addSpeakerToSession(10L, 1L);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().speakerEntities.size());
        assertEquals(speaker, result.get().speakerEntities.get(0));
    }

    @Test
    void testRemoveSpeakerFromSession() {
        session.speakerEntities.add(speaker);
        when(sessionRepository.findByIdOptional(10L)).thenReturn(Optional.of(session));
        when(speakerRepository.findByIdOptional(1L)).thenReturn(Optional.of(speaker));
        Optional<Session> result = sessionService.removeSpeakerFromSession(10L, 1L);
        assertTrue(result.isPresent());
        assertEquals(0, result.get().speakerEntities.size());
    }

    @Test
    void testAddSpeakerToSession_SpeakerNotFound() {
        when(sessionRepository.findByIdOptional(10L)).thenReturn(Optional.of(session));
        when(speakerRepository.findByIdOptional(2L)).thenReturn(Optional.empty());
        Optional<Session> result = sessionService.addSpeakerToSession(10L, 2L);
        assertFalse(result.isPresent());
    }

    @Test
    void testRemoveSpeakerFromSession_SessionNotFound() {
        when(sessionRepository.findByIdOptional(99L)).thenReturn(Optional.empty());
        Optional<Session> result = sessionService.removeSpeakerFromSession(99L, 1L);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindSessionsBySpeaker() {
        List<Session> sessions = List.of(session);
        when(sessionRepository.find("SELECT s FROM Session s JOIN s.speakers sp WHERE sp.id = ?1", 1L)).thenReturn(mock(io.quarkus.hibernate.orm.panache.PanacheQuery.class));
        when(sessionRepository.find("SELECT s FROM Session s JOIN s.speakers sp WHERE sp.id = ?1", 1L).list()).thenReturn(sessions);
        List<Session> found = sessionService.findSessionsBySpeaker(1L);
        assertEquals(1, found.size());
    }
} 