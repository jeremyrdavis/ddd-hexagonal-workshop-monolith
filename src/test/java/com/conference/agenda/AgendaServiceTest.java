package com.conference.agenda;

import com.conference.sessions.Session;
import com.conference.sessions.SessionRepository;
import io.arrogantprogrammer.speakers.Speaker;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.emptyArray;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.withSettings;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

@QuarkusTest
public class AgendaServiceTest {

    @InjectMock
    SessionRepository sessionRepository;

    @Inject
    AgendaService agendaService;

    Session session1, session2, session3, session4;
    Speaker speaker1, speaker2;

    @BeforeEach
    void setUp() {
        // setup speakers
        speaker1 = new Speaker();
        speaker1.name = "Alice";
        speaker1.title = "Engineer";
        speaker1.company = "Acme";
        speaker2 = new Speaker();
        speaker2.name = "Bob";
        speaker2.title = "CTO";
        speaker2.company = "Beta";
        session1 = new Session();
        session1.id = 1L;
        session1.title = "Keynote: Opening";
        session1.startTime = LocalDateTime.of(2025, 5, 14, 9, 0);
        session1.endTime = LocalDateTime.of(2025, 5, 14, 10, 0);
        session1.room = "Main";
        session1.speakers = List.of(speaker1);
        session2 = new Session();
        session2.id = 2L;
        session2.title = "Break";
        session2.startTime = LocalDateTime.of(2025, 5, 14, 10, 0);
        session2.endTime = LocalDateTime.of(2025, 5, 14, 10, 30);
        session2.room = "Lobby";
        session2.speakers = new ArrayList<>();
        session3 = new Session();
        session3.id = 3L;
        session3.title = "DDD Deep Dive";
        session3.startTime = LocalDateTime.of(2025, 5, 14, 10, 30);
        session3.endTime = LocalDateTime.of(2025, 5, 14, 11, 30);
        session3.room = "Track 1";
        session3.speakers = List.of(speaker2);
        session4 = new Session();
        session4.id = 4L;
        session4.title = "More DDD!";
        session4.startTime = LocalDateTime.of(2025, 5, 14, 13, 30);
        session4.endTime = LocalDateTime.of(2025, 5, 14, 14, 30);
        session4.room = "Track 1";
        session4.speakers = Collections.emptyList();

        PanacheQuery orderByStartTime = mockQuery(List.of(session1, session2, session3, session4));
        Mockito.when(sessionRepository.find("ORDER BY startTime")).thenReturn(orderByStartTime);

        PanacheQuery orderByStartTimeBetween = mockQuery(List.of(session1));
        Mockito.when(sessionRepository.find("startTime >= ?1 AND startTime < ?2 ORDER BY startTime", 
        LocalDate.of(2025, 5, 14).atStartOfDay(), 
        LocalDate.of(2025, 5, 15).atStartOfDay())).thenReturn(orderByStartTimeBetween);

        PanacheQuery orderByStartTimeAndRoom = mockQuery(List.of(session3));
        Mockito.when(sessionRepository.find("room = ?1 ORDER BY startTime", "Track 1")).thenReturn(orderByStartTimeAndRoom);

        QuarkusMock.installMockForType(sessionRepository, SessionRepository.class);
    }

    @Test
    void testAgendaAssemblyAndOrdering() {
        List<AgendaItem> agenda = agendaService.getFullAgenda();
        assertEquals(4, agenda.size());
        assertEquals("Keynote: Opening", agenda.get(0).title);
        assertEquals("Break", agenda.get(1).title);
        assertEquals("DDD Deep Dive", agenda.get(2).title);
    }

    @Test
    void testGroupingByTimeSlot() {
        List<AgendaItem> agenda = agendaService.getFullAgenda();
        Map<LocalDateTime, List<AgendaItem>> grouped = agendaService.groupByTimeSlot(agenda);
        assertTrue(grouped.containsKey(session1.startTime));
        assertEquals(1, grouped.get(session1.startTime).size());
    }

    @Test
    void testMissingSpeakerData() {
        List<AgendaItem> agenda = agendaService.getFullAgenda();
        assertEquals(4, agenda.size());
        assertNotNull(agenda.get(3).speakers);
        assertEquals(0, agenda.get(3).speakers.size());
    }

    @Test
    void testFilterByDay() {
        List<AgendaItem> agenda = agendaService.getAgendaForDay(LocalDate.of(2025, 5, 14));
        assertEquals(1, agenda.size());
        assertEquals("Keynote: Opening", agenda.get(0).title);
    }

    @Test
    void testFilterByTrack() {
        List<AgendaItem> agenda = agendaService.getAgendaForTrack("Track 1");
        assertEquals(1, agenda.size());
        assertEquals("DDD Deep Dive", agenda.get(0).title);
    }

    @Test
    void testKeynoteAndBreakIdentification() {
        List<AgendaItem> agenda = agendaService.getFullAgenda();
        assertTrue(agenda.get(0).keynote);
        assertFalse(agenda.get(0).isBreak);
        assertFalse(agenda.get(1).keynote);
        assertTrue(agenda.get(1).isBreak);
    }

    // Helper to mock PanacheQuery
    private <T> io.quarkus.hibernate.orm.panache.PanacheQuery<T> mockQuery(List<T> list) {
        io.quarkus.hibernate.orm.panache.PanacheQuery<T> query = mock(io.quarkus.hibernate.orm.panache.PanacheQuery.class, withSettings().defaultAnswer(RETURNS_DEEP_STUBS));
        when(query.list()).thenReturn(list);
        return query;
    }
} 