package io.arrogantprogrammer.cfp.domain.repositories;

import io.arrogantprogrammer.cfp.domain.aggregates.ConferenceSession;
import io.arrogantprogrammer.cfp.domain.aggregates.Speaker;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for ConferenceSession aggregate.
 */
public interface ConferenceSessionRepository {
    
    /**
     * Saves a conference session.
     * 
     * @param session the session to save
     * @return the saved session
     */
    ConferenceSession save(ConferenceSession session);
    
    /**
     * Finds a session by ID.
     * 
     * @param id the ID to search for
     * @return an optional containing the session if found
     */
    Optional<ConferenceSession> findById(Long id);
    
    /**
     * Finds sessions by speaker.
     * 
     * @param speaker the speaker to search for
     * @return a list of sessions for the speaker
     */
    List<ConferenceSession> findBySpeaker(Speaker speaker);
    
    /**
     * Finds sessions by speaker ID.
     * 
     * @param speakerId the ID of the speaker
     * @return a list of sessions for the speaker
     */
    List<ConferenceSession> findBySpeakerId(Long speakerId);
    
    /**
     * Finds sessions by status.
     * 
     * @param status the session status
     * @return a list of sessions with the given status
     */
    List<ConferenceSession> findByStatus(ConferenceSession.SessionStatus status);
    
    /**
     * Finds sessions by type.
     * 
     * @param type the session type
     * @return a list of sessions with the given type
     */
    List<ConferenceSession> findByType(ConferenceSession.SessionType type);
    
    /**
     * Finds sessions by level.
     * 
     * @param level the session level
     * @return a list of sessions with the given level
     */
    List<ConferenceSession> findByLevel(ConferenceSession.SessionLevel level);
    
    /**
     * Gets all sessions.
     * 
     * @return a list of all sessions
     */
    List<ConferenceSession> findAll();
    
    /**
     * Deletes a session by ID.
     * 
     * @param id the ID of the session to delete
     * @return true if the session was deleted, false if it was not found
     */
    boolean deleteById(Long id);
}
