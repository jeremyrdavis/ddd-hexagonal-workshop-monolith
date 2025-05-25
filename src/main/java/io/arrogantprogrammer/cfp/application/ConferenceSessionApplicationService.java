package io.arrogantprogrammer.cfp.application;

import io.arrogantprogrammer.cfp.api.dto.ConferenceSessionDTO;
import io.arrogantprogrammer.cfp.api.dto.SpeakerDTO;
import io.arrogantprogrammer.cfp.domain.aggregates.ConferenceSession;
import io.arrogantprogrammer.cfp.domain.aggregates.Speaker;
import io.arrogantprogrammer.cfp.domain.repositories.ConferenceSessionRepository;
import io.arrogantprogrammer.cfp.domain.repositories.SpeakerRepository;
import io.arrogantprogrammer.cfp.domain.valueobjects.SessionAbstract;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Application service for conference session management.
 */
@ApplicationScoped
public class ConferenceSessionApplicationService {
    
    @Inject
    ConferenceSessionRepository conferenceSessionRepository;
    
    @Inject
    SpeakerRepository speakerRepository;
    
    /**
     * Gets all conference sessions.
     * 
     * @return a list of all conference sessions
     */
    public List<ConferenceSessionDTO> getAllSessions() {
        return conferenceSessionRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets a conference session by ID.
     * 
     * @param id the session ID
     * @return an optional containing the session if found
     */
    public Optional<ConferenceSessionDTO> getSession(Long id) {
        return conferenceSessionRepository.findById(id).map(this::mapToDTO);
    }
    
    /**
     * Creates a new conference session.
     * 
     * @param sessionDTO the session to create
     * @return the created session
     */
    @Transactional
    public ConferenceSessionDTO createSession(ConferenceSessionDTO sessionDTO) {
        SessionAbstract sessionAbstract = new SessionAbstract(
                sessionDTO.title(),
                sessionDTO.summary(),
                sessionDTO.outline(),
                sessionDTO.learningObjectives(),
                sessionDTO.targetAudience(),
                sessionDTO.prerequisites()
        );
        
        ConferenceSession session = new ConferenceSession(
                sessionAbstract,
                sessionDTO.sessionType(),
                sessionDTO.sessionLevel(),
                Duration.ofMinutes(sessionDTO.durationMinutes())
        );
        
        ConferenceSession savedSession = conferenceSessionRepository.save(session);
        return mapToDTO(savedSession);
    }
    
    /**
     * Updates an existing conference session.
     * 
     * @param id the ID of the session to update
     * @param sessionDTO the updated session data
     * @return an optional containing the updated session if found
     */
    @Transactional
    public Optional<ConferenceSessionDTO> updateSession(Long id, ConferenceSessionDTO sessionDTO) {
        return conferenceSessionRepository.findById(id).map(session -> {
            SessionAbstract sessionAbstract = new SessionAbstract(
                    sessionDTO.title(),
                    sessionDTO.summary(),
                    sessionDTO.outline(),
                    sessionDTO.learningObjectives(),
                    sessionDTO.targetAudience(),
                    sessionDTO.prerequisites()
            );
            
            session.updateSessionAbstract(sessionAbstract);
            session.updateSessionType(sessionDTO.sessionType());
            session.updateSessionLevel(sessionDTO.sessionLevel());
            session.updateDuration(Duration.ofMinutes(sessionDTO.durationMinutes()));
            
            ConferenceSession updatedSession = conferenceSessionRepository.save(session);
            return mapToDTO(updatedSession);
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
        return conferenceSessionRepository.deleteById(id);
    }
    
    /**
     * Adds a speaker to a session.
     * 
     * @param sessionId the ID of the session
     * @param speakerId the ID of the speaker
     * @return an optional containing the updated session if both session and speaker were found
     */
    @Transactional
    public Optional<ConferenceSessionDTO> addSpeakerToSession(Long sessionId, Long speakerId) {
        Optional<ConferenceSession> sessionOpt = conferenceSessionRepository.findById(sessionId);
        Optional<Speaker> speakerOpt = speakerRepository.findById(speakerId);
        
        if (sessionOpt.isPresent() && speakerOpt.isPresent()) {
            ConferenceSession session = sessionOpt.get();
            Speaker speaker = speakerOpt.get();
            
            if (session.addSpeaker(speaker)) {
                ConferenceSession updatedSession = conferenceSessionRepository.save(session);
                return Optional.of(mapToDTO(updatedSession));
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
    public Optional<ConferenceSessionDTO> removeSpeakerFromSession(Long sessionId, Long speakerId) {
        Optional<ConferenceSession> sessionOpt = conferenceSessionRepository.findById(sessionId);
        Optional<Speaker> speakerOpt = speakerRepository.findById(speakerId);
        
        if (sessionOpt.isPresent() && speakerOpt.isPresent()) {
            ConferenceSession session = sessionOpt.get();
            Speaker speaker = speakerOpt.get();
            
            if (session.removeSpeaker(speaker)) {
                ConferenceSession updatedSession = conferenceSessionRepository.save(session);
                return Optional.of(mapToDTO(updatedSession));
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
    public Optional<ConferenceSessionDTO> acceptSession(Long id) {
        return conferenceSessionRepository.findById(id).map(session -> {
            session.accept();
            ConferenceSession updatedSession = conferenceSessionRepository.save(session);
            return mapToDTO(updatedSession);
        });
    }
    
    /**
     * Rejects a session.
     * 
     * @param id the ID of the session to reject
     * @return an optional containing the updated session if found
     */
    @Transactional
    public Optional<ConferenceSessionDTO> rejectSession(Long id) {
        return conferenceSessionRepository.findById(id).map(session -> {
            session.reject();
            ConferenceSession updatedSession = conferenceSessionRepository.save(session);
            return mapToDTO(updatedSession);
        });
    }
    
    /**
     * Withdraws a session.
     * 
     * @param id the ID of the session to withdraw
     * @return an optional containing the updated session if found
     */
    @Transactional
    public Optional<ConferenceSessionDTO> withdrawSession(Long id) {
        return conferenceSessionRepository.findById(id).map(session -> {
            session.withdraw();
            ConferenceSession updatedSession = conferenceSessionRepository.save(session);
            return mapToDTO(updatedSession);
        });
    }
    
    /**
     * Finds sessions by speaker ID.
     * 
     * @param speakerId the ID of the speaker
     * @return a list of sessions for the speaker
     */
    public List<ConferenceSessionDTO> findSessionsBySpeaker(Long speakerId) {
        return conferenceSessionRepository.findBySpeakerId(speakerId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Finds sessions by status.
     * 
     * @param status the session status
     * @return a list of sessions with the given status
     */
    public List<ConferenceSessionDTO> findSessionsByStatus(ConferenceSession.SessionStatus status) {
        return conferenceSessionRepository.findByStatus(status).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Maps a ConferenceSession domain object to a ConferenceSessionDTO.
     * 
     * @param session the session to map
     * @return the session DTO
     */
    private ConferenceSessionDTO mapToDTO(ConferenceSession session) {
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
                session.getSpeakers().stream()
                        .map(speaker -> new SpeakerDTO(
                                speaker.getName(),
                                speaker.getEmail(),
                                speaker.getBio(),
                                speaker.getCompany(),
                                speaker.getTitle(),
                                speaker.getPhotoUrl()
                        ))
                        .collect(Collectors.toList())
        );
    }
}
