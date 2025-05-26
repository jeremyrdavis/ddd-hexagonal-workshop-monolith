package io.arrogantprogrammer.cfp.infrastructure.persistence;

import io.arrogantprogrammer.cfp.domain.aggregates.ConferenceSession;
import io.arrogantprogrammer.cfp.domain.aggregates.Speaker;
import io.arrogantprogrammer.cfp.domain.valueobjects.SessionAbstract;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JPA implementation of the ConferenceSessionRepository interface using Panache.
 */
@ApplicationScoped
public class ConferenceSessionRepository implements PanacheRepositoryBase<ConferenceSessionEntity> {
    
    @Inject
    JpaSpeakerRepository speakerRepository;
    

    public List<ConferenceSession> findBySpeaker(Speaker speaker) {
        return find("SELECT s FROM ConferenceSessionEntity s JOIN s.speakers sp WHERE sp.id = ?1", speaker.getId())
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    public List<ConferenceSession> findBySpeakerId(Long speakerId) {
        return find("SELECT s FROM ConferenceSessionEntity s JOIN s.speakers sp WHERE sp.id = ?1", speakerId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    public List<ConferenceSession> findByStatus(ConferenceSession.SessionStatus status) {
        return list("status", status)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    public List<ConferenceSession> findByType(ConferenceSession.SessionType type) {
        return list("sessionType", type)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    public List<ConferenceSession> findByLevel(ConferenceSession.SessionLevel level) {
        return list("sessionLevel", level)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public boolean deleteById(Long id) {
        return deleteById(id);
    }
    
    /**
     * Maps a ConferenceSession domain object to a ConferenceSessionEntity.
     * 
     * @param session the session to map
     * @return the session entity
     */
    private ConferenceSessionEntity mapToEntity(ConferenceSession session) {
        ConferenceSessionEntity entity = new ConferenceSessionEntity();
        entity.setId(session.getId());
        entity.setSessionAbstract(new SessionAbstractEntity(
                session.getSessionAbstract().title(),
                session.getSessionAbstract().summary(),
                session.getSessionAbstract().outline(),
                session.getSessionAbstract().learningObjectives(),
                session.getSessionAbstract().targetAudience(),
                session.getSessionAbstract().prerequisites()
        ));
        entity.setSessionType(session.getSessionType());
        entity.setSessionLevel(session.getSessionLevel());
        entity.setDuration(session.getDuration());
        entity.setStatus(session.getStatus());
        
        // Map speakers
        List<SpeakerEntity> speakerEntities = session.getSpeakers().stream()
                .map(speaker -> speakerRepository.findByIdOptional(speaker.getId())
                        .orElseThrow(() -> new IllegalStateException("Speaker not found with ID: " + speaker.getId())))
                .collect(Collectors.toList());
        entity.setSpeakers(speakerEntities);
        
        return entity;
    }
    
    /**
     * Maps a ConferenceSessionEntity to a ConferenceSession domain object.
     * 
     * @param entity the entity to map
     * @return the session domain object
     */
    private ConferenceSession mapToDomain(ConferenceSessionEntity entity) {
        SessionAbstract sessionAbstract = new SessionAbstract(
                entity.getTitle(),
                entity.getSessionAbstract().getSummary(),
                entity.getSessionAbstract().getOutline(),
                entity.getSessionAbstract().getLearningObjectives(),
                entity.getSessionAbstract().getTargetAudience(),
                entity.getSessionAbstract().getPrerequisites()
        );
        
        ConferenceSession session = new ConferenceSession(
                sessionAbstract,
                entity.getSessionType(),
                entity.getSessionLevel(),
                entity.getDuration()
        );
        session.setId(entity.getId());
        
        // Map speakers
        entity.getSpeakers().forEach(speakerEntity -> {
            Speaker speaker = speakerRepository.mapToDomain(speakerEntity);
            session.addSpeaker(speaker);
        });
        
        return session;
    }
}
