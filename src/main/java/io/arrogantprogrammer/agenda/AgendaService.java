package io.arrogantprogrammer.agenda;

import io.arrogantprogrammer.sessions.Session;
import io.arrogantprogrammer.sessions.SessionRepository;
import io.arrogantprogrammer.speakers.Speaker;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class AgendaService {
    SessionRepository sessionRepository;

    @Inject
    public AgendaService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<AgendaItem> getFullAgenda() {
        List<Session> sessions = sessionRepository.find("ORDER BY startTime").list();
        return sessions.stream().map(this::toAgendaItem).collect(Collectors.toList());
    }

    public List<AgendaItem> getAgendaForDay(LocalDate date) {
        List<Session> sessions = sessionRepository.find("startTime >= ?1 AND startTime < ?2 ORDER BY startTime",
                date.atStartOfDay(), date.plusDays(1).atStartOfDay()).list();
        return sessions.stream().map(this::toAgendaItem).collect(Collectors.toList());
    }

    public List<AgendaItem> getAgendaForTrack(String track) {
        List<Session> sessions = sessionRepository.find("room = ?1 ORDER BY startTime", track).list();
        return sessions.stream().map(this::toAgendaItem).collect(Collectors.toList());
    }

    public Map<LocalDateTime, List<AgendaItem>> groupByTimeSlot(List<AgendaItem> items) {
        return items.stream().collect(Collectors.groupingBy(item -> item.startTime));
    }

    private AgendaItem toAgendaItem(Session session) {
        AgendaItem item = new AgendaItem();
        item.sessionId = session.id;
        item.title = session.title;
        item.startTime = session.startTime;
        item.endTime = session.endTime;
        item.room = session.room;
        item.speakers = session.speakerEntities == null ? List.of() : session.speakerEntities.stream().map(this::toSpeakerDTO).collect(Collectors.toList());
        item.keynote = isKeynote(session);
        item.isBreak = isBreak(session);
        return item;
    }

    private AgendaSpeakerDTO toSpeakerDTO(Speaker speaker) {
        AgendaSpeakerDTO dto = new AgendaSpeakerDTO();
        dto.name = speaker.name;
        dto.title = speaker.title;
        dto.company = speaker.company;
        return dto;
    }

    private boolean isKeynote(Session session) {
        return session.title != null && session.title.toLowerCase().contains("keynote");
    }

    private boolean isBreak(Session session) {
        return session.title != null && (
            session.title.toLowerCase().contains("break") ||
            session.title.toLowerCase().contains("lunch") ||
            session.title.toLowerCase().contains("coffee")
        );
    }
} 