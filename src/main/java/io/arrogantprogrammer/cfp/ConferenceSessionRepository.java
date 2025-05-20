package io.arrogantprogrammer.cfp;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * Repository for ConferenceSession entities.
 */
@ApplicationScoped
public class ConferenceSessionRepository implements PanacheRepository<ConferenceSession> {
    
    /**
     * Finds sessions by speaker ID.
     * 
     * @param speakerId the ID of the speaker
     * @return a list of sessions for the speaker
     */
    public List<ConferenceSession> findBySpeakerId(Long speakerId) {
        return find("SELECT s FROM ConferenceSession s JOIN s.speakers sp WHERE sp.id = ?1", speakerId).list();
    }
    
    /**
     * Finds sessions by status.
     * 
     * @param status the session status
     * @return a list of sessions with the given status
     */
    public List<ConferenceSession> findByStatus(ConferenceSession.SessionStatus status) {
        return find("status", status).list();
    }
    
    /**
     * Finds sessions by type.
     * 
     * @param type the session type
     * @return a list of sessions with the given type
     */
    public List<ConferenceSession> findByType(ConferenceSession.SessionType type) {
        return find("sessionType", type).list();
    }
    
    /**
     * Finds sessions by level.
     * 
     * @param level the session level
     * @return a list of sessions with the given level
     */
    public List<ConferenceSession> findByLevel(ConferenceSession.SessionLevel level) {
        return find("sessionLevel", level).list();
    }
}