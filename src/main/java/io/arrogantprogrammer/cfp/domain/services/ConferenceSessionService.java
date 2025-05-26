package io.arrogantprogrammer.cfp.domain.services;

import io.arrogantprogrammer.cfp.ConferenceSession;
import io.arrogantprogrammer.cfp.ConferenceSessionDTO;
import io.arrogantprogrammer.cfp.ConferenceSessionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for managing conference sessions.
 */
@ApplicationScoped
public class ConferenceSessionService {
    
    @Inject
    ConferenceSessionRepository conferenceSessionRepository;
    
    /**
     * Gets all conference sessions.
     * 
     * @return a list of all conference sessions
     */
    public List<ConferenceSessionDTO> getAllSessions() {
        return conferenceSessionRepository.streamAll().map(this::toConferenceSessionDTO).collect(Collectors.toList());
    }
    
    /**
     * Gets a conference session by ID.
     * 
     * @param id the session ID
     * @return an optional containing the session if found
     */
    public Optional<ConferenceSessionDTO> getSession(Long id) {
        return conferenceSessionRepository.findByIdOptional(id).map(this::toConferenceSessionDTO);
    }
    
    /**
     * Creates a new conference session.
     * 
     * @param session the session to create
     * @return the created session
     */
    @Transactional
    public ConferenceSessionDTO createSession(ConferenceSessionDTO session) {
        SessionAbstractEntity sessionAbstractEntity = new SessionAbstractEntity(
                session.title(),
                session.summary(),
                session.outline(),
                session.learningObjectives(),
                session.targetAudience(),
                session.prerequisites()
        );
        ConferenceSession conferenceSession = new ConferenceSession(
                sessionAbstractEntity,
                session.sessionType(),
                session.sessionLevel(),
                Duration.ofMinutes(session.durationMinutes())
        );
        conferenceSessionRepository.persist(conferenceSession);
        return toConferenceSessionDTO(conferenceSession);
    }
    
    /**
     * Updates an existing conference session.
     * 
     * @param id the ID of the session to update
     * @param session the updated session data
     * @return an optional containing the updated session if found
     */
//    @Transactional
//    public Optional<ConferenceSession> updateSession(Long id, ConferenceSessionDTO session) {
//        return sessionRepository.findByIdOptional(id)
//                .map(existing -> {
//                    existing.updateSessionAbstract(session.());
//                    existing.updateSessionType(session.getSessionType());
//                    existing.updateSessionLevel(session.getSessionLevel());
//                    existing.updateDuration(session.getDuration());
//                    return existing;
//                });
//    }
    
    /**
     * Deletes a conference session.
     * 
     * @param id the ID of the session to delete
     * @return true if the session was deleted, false if it was not found
     */
    @Transactional
    public boolean deleteSession(Long id) {
        return conferenceSessionRepository.deleteById(id);
    }
    
    /**
     * Adds a speaker to a session.
     * 
     * @param sessionId the ID of the session
     * @param speakerId the ID of the speaker
     * @return an optional containing the updated session if both session and speaker were found
     */
//    @Transactional
//    public Optional<ConferenceSessionDTO> addSpeakerToSession(Long sessionId, Long speakerId) {
//        Optional<ConferenceSession> sessionOpt = sessionRepository.findByIdOptional(sessionId);
//        Optional<SpeakerEntity> speakerOpt = speakerRepository.findByIdOptional(speakerId);
//
//        if (sessionOpt.isPresent() && speakerOpt.isPresent()) {
//            ConferenceSession session = sessionOpt.get();
//            SpeakerEntity speakerEntity = speakerOpt.get();
//
//            if (session.addSpeaker(speakerEntity)) {
//                return Optional.of(toConferenceSessionDTO(session));
//            }
//        }
//
//        return Optional.empty();
//    }
    
    /**
     * Removes a speaker from a session.
     * 
     * @param sessionId the ID of the session
     * @param speakerId the ID of the speaker
     * @return an optional containing the updated session if both session and speaker were found
     */
//    @Transactional
//    public Optional<ConferenceSessionDTO> removeSpeakerFromSession(Long sessionId, Long speakerId) {
//        Optional<ConferenceSession> sessionOpt = sessionRepository.findByIdOptional(sessionId);
//        Optional<SpeakerEntity> speakerOpt = speakerRepository.findByIdOptional(speakerId);
//
//        if (sessionOpt.isPresent() && speakerOpt.isPresent()) {
//            ConferenceSession session = sessionOpt.get();
//            SpeakerEntity speakerEntity = speakerOpt.get();
//
//            if (session.removeSpeaker(speakerEntity)) {
//                return Optional.of(toConferenceSessionDTO(session));
//            }
//        }
//
//        return Optional.empty();
//    }
    
    /**
     * Accepts a session.
     * 
     * @param id the ID of the session to accept
     * @return an optional containing the updated session if found
     */
//    @Transactional
//    public Optional<ConferenceSessionDTO> acceptSession(Long id) {
//        return .findByIdOptional(id)
//                .map(this::toConferenceSessionDTO);
//    }
    
    /**
     * Rejects a session.
     * 
     * @param id the ID of the session to reject
     * @return an optional containing the updated session if found
     */
//    @Transactional
//    public Optional<ConferenceSessionDTO> rejectSession(Long id) {
//        return conferenceSessionRepository.findByIdOptional(id)
//                .map(this::toConferenceSessionDTO);
//    }
    
    /**
     * Withdraws a session.
     * 
     * @param id the ID of the session to withdraw
     * @return an optional containing the updated session if found
     */
//    @Transactional
//    public Optional<ConferenceSessionDTO> withdrawSession(Long id) {
//        return conferenceSessionRepository.findByIdOptional(id)
//                .map(this::toConferenceSessionDTO);
//    }
    
    /**
     * Finds sessions by speaker ID.
     * 
     * @param speakerId the ID of the speaker
     * @return a list of sessions for the speaker
     */
    public List<ConferenceSessionDTO> findSessionsBySpeaker(Long speakerId) {
        return conferenceSessionRepository.findBySpeakerId(speakerId).stream()
                .map(this::toConferenceSessionDTO)
                .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Finds sessions by status.
     * 
     * @param status the session status
     * @return a list of sessions with the given status
     */
    public List<ConferenceSessionDTO> findSessionsByStatus(ConferenceSession.SessionStatus status) {
        return conferenceSessionRepository.findByStatus(status).stream()
                .map(this::toConferenceSessionDTO)
                    .collect(java.util.stream.Collectors.toList());
    }

    private ConferenceSessionDTO toConferenceSessionDTO(ConferenceSession session) {
        return new ConferenceSessionDTO(
                session.getId(),
                session.getSessionAbstract().getTitle(),
                session.getSessionAbstract().getSummary(),
                session.getSessionAbstract().getOutline(),
                session.getSessionAbstract().getLearningObjectives(),
                session.getSessionAbstract().getTargetAudience(),
                session.getSessionAbstract().getPrerequisites(),
                session.getSessionType(),
                session.getSessionLevel(),
                session.getDuration().toMinutes(),
                session.getStatus().name(),
                session.getSpeakers().stream().map(speaker -> new io.arrogantprogrammer.cfp.SpeakerDTO(
                        speaker.getName(),
                        speaker.getEmail(),
                        speaker.getBio(),
                        speaker.getCompany(),
                        speaker.getTitle(),
                        speaker.getPhotoUrl()
                )).collect(java.util.stream.Collectors.toList())
        );
    }

}