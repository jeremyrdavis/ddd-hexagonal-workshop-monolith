package io.arrogantprogrammer.cfp.infrastructure.persistence;

import io.arrogantprogrammer.cfp.domain.aggregates.ConferenceSession;
import io.arrogantprogrammer.cfp.domain.aggregates.Speaker;
import io.arrogantprogrammer.cfp.domain.repositories.ConferenceSessionRepository;
import io.arrogantprogrammer.cfp.domain.valueobjects.SessionAbstract;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JPA implementation of the ConferenceSessionRepository interface.
 */
@ApplicationScoped
public class JpaConferenceSessionRepository implements ConferenceSessionRepository, PanacheRepository<ConferenceSessionEntity> {
    
    @Inject
    EntityManager entityManager;
    
    @Inject
    JpaSpeakerRepository speakerRepository;
    
    @Override
    public ConferenceSession save(ConferenceSession session) {
        ConferenceSessionEntity entity = mapToEntity(session);
        if (entity.getId() == null) {
            persist(entity);
        } else {
            entity = getEntityManager().merge(entity);
        }
        return mapToDomain(entity);
    }
    
    @Override
    public Optional<ConferenceSession> findById(Long id) {
        return findByIdOptional(id).map(this::mapToDomain);
    }
    
    @Override
    public List<ConferenceSession> findBySpeaker(Speaker speaker) {
        return find("SELECT s FROM ConferenceSessionEntity s JOIN s.speakers sp WHERE sp.id = ?1", speaker.getId()).list().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConferenceSession> findBySpeakerId(Long speakerId) {
        return find("SELECT s FROM ConferenceSessionEntity s JOIN s.speakers sp WHERE sp.id = ?1", speakerId).list().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConferenceSession> findByStatus(ConferenceSession.SessionStatus status) {
        return find("status", status).list().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConferenceSession> findByType(ConferenceSession.SessionType type) {
        return find("sessionType", type).list().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConferenceSession> findByLevel(ConferenceSession.SessionLevel level) {
        return find("sessionLevel", level).list().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConferenceSession> findAll() {
        return listAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
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
                session.getSessionAbstract().getTitle(),
                session.getSessionAbstract().getSummary(),
                session.getSessionAbstract().getOutline(),
                session.getSessionAbstract().getLearningObjectives(),
                session.getSessionAbstract().getTargetAudience(),
                session.getSessionAbstract().getPrerequisites()
        ));
        entity.setSessionType(session.getSessionType());
        entity.setSessionLevel(session.getSessionLevel());
        entity.setDuration(session.getDuration());
        entity.setStatus(session.getStatus());
        
        // Map speakers
        List<SpeakerEntity> speakerEntities = session.getSpeakers().stream()
                .map(speaker -> speakerRepository.findByIdOptional(speaker.getId()).orElseThrow())
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
                entity.getSessionAbstract().getTitle(),
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
