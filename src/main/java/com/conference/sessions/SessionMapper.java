package com.conference.sessions;

import io.arrogantprogrammer.speakers.Speaker;
import io.arrogantprogrammer.speakers.SpeakerDTO;
import io.arrogantprogrammer.speakers.SpeakerMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SessionMapper {
    
    @Inject
    SpeakerMapper speakerMapper;
    
    public SessionDTO toDTO(Session session) {
        if (session == null) {
            return null;
        }
        
        SessionDTO dto = new SessionDTO();
        dto.id = session.id;
        dto.title = session.title;
        dto.description = session.description;
        dto.startTime = session.startTime;
        dto.endTime = session.endTime;
        dto.room = session.room;
        
        if (session.speakers != null) {
            dto.speakers = session.speakers.stream()
                .map(speakerMapper::toDTO)
                .toList();
        }
        
        return dto;
    }
    
    public Session toEntity(SessionDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Session session = new Session();
        session.id = dto.id;
        session.title = dto.title;
        session.description = dto.description;
        session.startTime = dto.startTime;
        session.endTime = dto.endTime;
        session.room = dto.room;
        
        return session;
    }
} 