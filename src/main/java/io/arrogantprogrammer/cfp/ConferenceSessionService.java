package io.arrogantprogrammer.cfp;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing conference sessions.
 */
@ApplicationScoped
public class ConferenceSessionService {
    
    @Inject
    ConferenceSessionRepository sessionRepository;
    
    @Inject
    SpeakerRepository speakerRepository;
    
    /**
     * Gets all conference sessions.
     * 
     * @return a list of all conference sessions
     */
    public List<ConferenceSession> getAllSessions() {
        return sessionRepository.listAll();
    }
    
    /**
     * Gets a conference session by ID.
     * 
     * @param id the session ID
     * @return an optional containing the session if found
     */
    public Optional<ConferenceSession> getSession(Long id) {
        return sessionRepository.findByIdOptional(id);
    }
    
    /**
     * Creates a new conference session.
     * 
     * @param session the session to create
     * @return the created session
     */
    @Transactional
    public ConferenceSession createSession(ConferenceSession session) {
        sessionRepository.persist(session);
        return session;
    }
    
    /**
     * Updates an existing conference session.
     * 
     * @param id the ID of the session to update
     * @param session the updated session data
     * @return an optional containing the updated session if found
     */
    @Transactional
    public Optional<ConferenceSession> updateSession(Long id, ConferenceSession session) {
        return sessionRepository.findByIdOptional(id)
                .map(existing -> {
                    existing.updateSessionAbstract(session.getSessionAbstract());
                    existing.updateSessionType(session.getSessionType());
                    existing.updateSessionLevel(session.getSessionLevel());
                    existing.updateDuration(session.getDuration());
                    return existing;
                });
    }
    
    /**
     * Deletes a conference session.
     * 
     * @param id the ID of the session to delete
     * @return true if the session was deleted, false if it was not found
     */
    @Transactional
    public boolean deleteSession(Long id) {
        return sessionRepository.deleteById(id);
    }
    
    /**
     * Adds a speaker to a session.
     * 
     * @param sessionId the ID of the session
     * @param speakerId the ID of the speaker
     * @return an optional containing the updated session if both session and speaker were found
     */
    @Transactional
    public Optional<ConferenceSession> addSpeakerToSession(Long sessionId, Long speakerId) {
        Optional<ConferenceSession> sessionOpt = sessionRepository.findByIdOptional(sessionId);
        Optional<Speaker> speakerOpt = speakerRepository.findByIdOptional(speakerId);
        
        if (sessionOpt.isPresent() && speakerOpt.isPresent()) {
            ConferenceSession session = sessionOpt.get();
            Speaker speaker = speakerOpt.get();
            
            if (session.addSpeaker(speaker)) {
                return Optional.of(session);
            }
        }
        
        return Optional.empty();
    }
    
    /**
     * Removes a speaker from a session.
     * 
     * @param sessionId the ID of the session
     * @param speakerId the ID of the speaker
     * @return an optional containing the updated session if both session and speaker were found
     */
    @Transactional
    public Optional<ConferenceSession> removeSpeakerFromSession(Long sessionId, Long speakerId) {
        Optional<ConferenceSession> sessionOpt = sessionRepository.findByIdOptional(sessionId);
        Optional<Speaker> speakerOpt = speakerRepository.findByIdOptional(speakerId);
        
        if (sessionOpt.isPresent() && speakerOpt.isPresent()) {
            ConferenceSession session = sessionOpt.get();
            Speaker speaker = speakerOpt.get();
            
            if (session.removeSpeaker(speaker)) {
                return Optional.of(session);
            }
        }
        
        return Optional.empty();
    }
    
    /**
     * Accepts a session.
     * 
     * @param id the ID of the session to accept
     * @return an optional containing the updated session if found
     */
    @Transactional
    public Optional<ConferenceSession> acceptSession(Long id) {
        return sessionRepository.findByIdOptional(id)
                .map(session -> {
                    session.accept();
                    return session;
                });
    }
    
    /**
     * Rejects a session.
     * 
     * @param id the ID of the session to reject
     * @return an optional containing the updated session if found
     */
    @Transactional
    public Optional<ConferenceSession> rejectSession(Long id) {
        return sessionRepository.findByIdOptional(id)
                .map(session -> {
                    session.reject();
                    return session;
                });
    }
    
    /**
     * Withdraws a session.
     * 
     * @param id the ID of the session to withdraw
     * @return an optional containing the updated session if found
     */
    @Transactional
    public Optional<ConferenceSession> withdrawSession(Long id) {
        return sessionRepository.findByIdOptional(id)
                .map(session -> {
                    session.withdraw();
                    return session;
                });
    }
    
    /**
     * Finds sessions by speaker ID.
     * 
     * @param speakerId the ID of the speaker
     * @return a list of sessions for the speaker
     */
    public List<ConferenceSession> findSessionsBySpeaker(Long speakerId) {
        return sessionRepository.findBySpeakerId(speakerId);
    }
    
    /**
     * Finds sessions by status.
     * 
     * @param status the session status
     * @return a list of sessions with the given status
     */
    public List<ConferenceSession> findSessionsByStatus(ConferenceSession.SessionStatus status) {
        return sessionRepository.findByStatus(status);
    }
}