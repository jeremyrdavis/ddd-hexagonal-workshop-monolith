package io.arrogantprogrammer.cfp;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for ConferenceSession entities and DTOs.
 */
@ApplicationScoped
public class ConferenceSessionMapper {
    
    @Inject
    SpeakerMapper speakerMapper;
    
    /**
     * Converts a ConferenceSession entity to a ConferenceSessionDTO.
     * 
     * @param session the entity to convert
     * @return the DTO
     */
    public ConferenceSessionDTO toDTO(ConferenceSession session) {
        if (session == null) {
            return null;
        }
        
        SessionAbstract abstract_ = session.getSessionAbstract();
        List<SpeakerDTO> speakerDTOs = session.getSpeakers().stream()
                .map(speakerMapper::toDTO)
                .collect(Collectors.toList());
        
        return new ConferenceSessionDTO(
            session.getId(),
            abstract_.getTitle(),
            abstract_.getSummary(),
            abstract_.getOutline(),
            abstract_.getLearningObjectives(),
            abstract_.getTargetAudience(),
            abstract_.getPrerequisites(),
            session.getSessionType().name(),
            session.getSessionLevel().name(),
            session.getDuration().toMinutes(),
            session.getStatus().name(),
            speakerDTOs
        );
    }
    
    /**
     * Converts a ConferenceSessionDTO to a ConferenceSession entity.
     * 
     * @param dto the DTO to convert
     * @return the entity
     */
    public ConferenceSession toEntity(ConferenceSessionDTO dto) {
        if (dto == null) {
            return null;
        }
        
        SessionAbstract abstract_ = new SessionAbstract(
            dto.title,
            dto.summary,
            dto.outline,
            dto.learningObjectives,
            dto.targetAudience,
            dto.prerequisites
        );
        
        ConferenceSession.SessionType sessionType = ConferenceSession.SessionType.valueOf(dto.sessionType);
        ConferenceSession.SessionLevel sessionLevel = ConferenceSession.SessionLevel.valueOf(dto.sessionLevel);
        Duration duration = Duration.ofMinutes(dto.durationMinutes);
        
        return new ConferenceSession(abstract_, sessionType, sessionLevel, duration);
    }
    
    /**
     * Updates an existing ConferenceSession entity with data from a ConferenceSessionDTO.
     * 
     * @param session the entity to update
     * @param dto the DTO with the new data
     * @return the updated entity
     */
    public ConferenceSession updateEntityFromDTO(ConferenceSession session, ConferenceSessionDTO dto) {
        if (session == null || dto == null) {
            return session;
        }
        
        SessionAbstract abstract_ = new SessionAbstract(
            dto.title,
            dto.summary,
            dto.outline,
            dto.learningObjectives,
            dto.targetAudience,
            dto.prerequisites
        );
        
        session.updateSessionAbstract(abstract_);
        session.updateSessionType(ConferenceSession.SessionType.valueOf(dto.sessionType));
        session.updateSessionLevel(ConferenceSession.SessionLevel.valueOf(dto.sessionLevel));
        session.updateDuration(Duration.ofMinutes(dto.durationMinutes));
        
        return session;
    }
}