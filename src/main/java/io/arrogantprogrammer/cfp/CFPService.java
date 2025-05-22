package io.arrogantprogrammer.cfp;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class CFPService {

    @Inject
    SpeakerRepository speakerRepository;

    @Inject
    ConferenceSessionRepository conferenceSessionRepository;

    @Transactional
    SpeakerDTO registerSpeaker(SpeakerDTO speakerDTO) {
        Speaker speaker = Speaker.create(speakerDTO.name(),speakerDTO.email(), speakerDTO.bio(), speakerDTO.company(), speakerDTO.title(), speakerDTO.photoUrl());
        speakerRepository.persist(speaker);
        return new SpeakerDTO(
            speaker.getName(),
            speaker.getEmail(),
            speaker.getBio(),
            speaker.getCompany(),
            speaker.getTitle(),
            speaker.getPhotoUrl()
        );
    }

    List<SpeakerDTO> getAllSpeakers() {
        return null;
    }

    Optional<SpeakerDTO> getSpeaker(Long id) {
        return speakerRepository.findByIdOptional(id)
                .map(speaker -> new SpeakerDTO(
                        speaker.getName(),
                        speaker.getEmail(),
                        speaker.getBio(),
                        speaker.getCompany(),
                        speaker.getTitle(),
                        speaker.getPhotoUrl()
                ));
    }

    @Transactional
    Optional<SpeakerDTO> updateSpeaker(long l, SpeakerDTO updatedSpeakerDTO) {
        Speaker speaker = speakerRepository.findByIdOptional(l).get();
        if(speaker != null) {
            speaker.updateName(updatedSpeakerDTO.name());
            speaker.updateEmail(updatedSpeakerDTO.email());
            speaker.updateBio(updatedSpeakerDTO.bio());
            speaker.updateCompany(updatedSpeakerDTO.company());
            speaker.updateTitle(updatedSpeakerDTO.title());
            speaker.updatePhotoUrl(updatedSpeakerDTO.photoUrl());
            speakerRepository.persist(speaker);
            return Optional.of(new SpeakerDTO(
                    speaker.getName(),
                    speaker.getEmail(),
                    speaker.getBio(),
                    speaker.getCompany(),
                    speaker.getTitle(),
                    speaker.getPhotoUrl()));
        }
        return Optional.empty();
    }

    boolean deleteSpeaker(final Long id) {
        if (speakerRepository.deleteById(id)) {
            return true;
        }
        return false;
    }

    /**
     * Finds speakers by company.
     *
     * @param company the company to search for
     * @return a list of speakers from the given company
     */
    public List<SpeakerDTO> findByCompany(String company) {
        return speakerRepository.findByCompany(company).stream().map(speaker -> {
            return new SpeakerDTO(
                    speaker.getName(),
                    speaker.getEmail(),
                    speaker.getBio(),
                    speaker.getCompany(),
                    speaker.getTitle(),
                    speaker.getPhotoUrl()
            );
        }).collect(Collectors.toList());
    }

    /**
     * Searches for speakers by name.
     *
     * @param nameQuery the name query to search for
     * @return a list of speakers matching the name query
     */
    public List<SpeakerDTO> searchByName(String nameQuery) {
        return speakerRepository.searchByName(nameQuery).stream().map(speaker -> {
            return new SpeakerDTO(
                    speaker.getName(),
                    speaker.getEmail(),
                    speaker.getBio(),
                    speaker.getCompany(),
                    speaker.getTitle(),
                    speaker.getPhotoUrl()
            );
        }).collect(Collectors.toList());
    }

    List<ConferenceSessionDTO> getAllConferenceSessions() {
        return conferenceSessionRepository.streamAll().map(conferenceSession -> {
            return  new ConferenceSessionDTO(
                    conferenceSession.getId(),
                    conferenceSession.getSessionAbstract().getTitle(),
                    conferenceSession.getSessionAbstract().getSummary(),
                    conferenceSession.getSessionAbstract().getOutline(),
                    conferenceSession.getSessionAbstract().getLearningObjectives(),
                    conferenceSession.getSessionAbstract().getTargetAudience(),
                    conferenceSession.getSessionAbstract().getPrerequisites(),
                    conferenceSession.getSessionType(),
                    conferenceSession.getSessionLevel(),
                    conferenceSession.getDuration().toMinutes(),
                    conferenceSession.getStatus().name(),
                    conferenceSession.getSpeakers().stream().map(speaker -> {
                        return new SpeakerDTO(
                                speaker.getName(),
                                speaker.getEmail(),
                                speaker.getBio(),
                                speaker.getCompany(),
                                speaker.getTitle(),
                                speaker.getPhotoUrl()
                        );
                    }).collect(Collectors.toList()));
        }).collect(Collectors.toList());
    }

    Optional<ConferenceSessionDTO> getSession(Long id) {
        return conferenceSessionRepository.findByIdOptional(id)
                .map(session -> new ConferenceSessionDTO(
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
                        session.getSpeakers().stream().map(speaker -> new SpeakerDTO(
                                speaker.getName(),
                                speaker.getEmail(),
                                speaker.getBio(),
                                speaker.getCompany(),
                                speaker.getTitle(),
                                speaker.getPhotoUrl()
                        )).collect(Collectors.toList())
                ));
    }

@Transactional
Optional<ConferenceSessionDTO> updateSession(long id, ConferenceSessionDTO updatedSession) {
    ConferenceSession existingSession = conferenceSessionRepository.findById(id);
    if (existingSession != null) {
        existingSession.updateSessionAbstract(new SessionAbstract(updatedSession.title(), updatedSession.summary(), updatedSession.outline(), updatedSession.learningObjectives(), updatedSession.targetAudience(), updatedSession.prerequisites()));
        existingSession.updateSessionType(updatedSession.sessionType());
        existingSession.updateSessionLevel(updatedSession.sessionLevel());
        existingSession.updateDuration(Duration.ofMinutes(updatedSession.durationMinutes()));
        existingSession.updateStatus(ConferenceSession.SessionStatus.valueOf(updatedSession.status()));

        conferenceSessionRepository.persist(existingSession);

        return Optional.of(new ConferenceSessionDTO(
                existingSession.getId(),
                existingSession.getSessionAbstract().getTitle(),
                existingSession.getSessionAbstract().getSummary(),
                existingSession.getSessionAbstract().getOutline(),
                existingSession.getSessionAbstract().getLearningObjectives(),
                existingSession.getSessionAbstract().getTargetAudience(),
                existingSession.getSessionAbstract().getPrerequisites(),
                existingSession.getSessionType(),
                existingSession.getSessionLevel(),
                existingSession.getDuration().toMinutes(),
                existingSession.getStatus().name(),
                existingSession.getSpeakers().stream().map(speaker -> new SpeakerDTO(
                        speaker.getName(),
                        speaker.getEmail(),
                        speaker.getBio(),
                        speaker.getCompany(),
                        speaker.getTitle(),
                        speaker.getPhotoUrl()
                )).collect(Collectors.toList())
        ));
    }
    return Optional.empty();
}

    ConferenceSessionDTO createSession(ConferenceSessionDTO dto) {
        ConferenceSession session = new ConferenceSession(
                new SessionAbstract(dto.title(), dto.summary(), dto.outline(), dto.learningObjectives(), dto.targetAudience(), dto.prerequisites()),
                dto.sessionType(),
                dto.sessionLevel(),
                Duration.ofMinutes(dto.durationMinutes()));
        conferenceSessionRepository.persist(session);
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
                List.of()
        );
    }

    boolean deleteSession(Long id) {
        if (conferenceSessionRepository.deleteById(id)) {
            return true;
        }
        return false;
    }

    /**
     * Adds a speaker to a session.
     *
     * @param sessionId the ID of the session
     * @param speakerId the ID of the speaker
     * @return an optional containing the updated session DTO if both session and speaker are found
     */
    @Transactional
    public Optional<ConferenceSessionDTO> addSpeakerToSession(Long sessionId, Long speakerId) {
        ConferenceSession session = conferenceSessionRepository.findById(sessionId);
        Speaker speaker = speakerRepository.findById(speakerId);

        if (session != null && speaker != null) {
            session.addSpeaker(speaker);
            conferenceSessionRepository.persist(session);
            return Optional.of(new ConferenceSessionDTO(
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
                    session.getSpeakers().stream().map(spkr -> new SpeakerDTO(
                            spkr.getName(),
                            spkr.getEmail(),
                            spkr.getBio(),
                            spkr.getCompany(),
                            spkr.getTitle(),
                            spkr.getPhotoUrl()
                    )).collect(Collectors.toList())
            ));
        }
        return Optional.empty();
    }

    /**
     * Removes a speaker from a session.
     *
     * @param sessionId the ID of the session
     * @param speakerId the ID of the speaker
     * @return an optional containing the updated session DTO if both session and speaker are found
     */
    @Transactional
    public Optional<ConferenceSessionDTO> removeSpeakerFromSession(Long sessionId, Long speakerId) {
        ConferenceSession session = conferenceSessionRepository.findById(sessionId);
        Speaker speaker = speakerRepository.findById(speakerId);

        if (session != null && speaker != null) {
            session.removeSpeaker(speaker);
            conferenceSessionRepository.persist(session);
            return Optional.of(new ConferenceSessionDTO(
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
                    session.getSpeakers().stream().map(spkr -> new SpeakerDTO(
                            spkr.getName(),
                            spkr.getEmail(),
                            spkr.getBio(),
                            spkr.getCompany(),
                            spkr.getTitle(),
                            spkr.getPhotoUrl()
                    )).collect(Collectors.toList())
            ));
        }
        return Optional.empty();
    }

    /**
     * Accepts a session.
     *
     * @param sessionId the ID of the session to accept
     * @return an optional containing the updated session DTO if the session is found
     */
    @Transactional
    public Optional<ConferenceSessionDTO> acceptSession(Long sessionId) {
        ConferenceSession session = conferenceSessionRepository.findById(sessionId);

        if (session != null) {
            session.accept();
            conferenceSessionRepository.persist(session);
            return Optional.of(new ConferenceSessionDTO(
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
                    session.getSpeakers().stream().map(spkr -> new SpeakerDTO(
                            spkr.getName(),
                            spkr.getEmail(),
                            spkr.getBio(),
                            spkr.getCompany(),
                            spkr.getTitle(),
                            spkr.getPhotoUrl()
                    )).collect(Collectors.toList())
            ));
        }
        return Optional.empty();
    }

    /**
     * Rejects a session.
     *
     * @param sessionId the ID of the session to reject
     * @return an optional containing the updated session DTO if the session is found
     */
    @Transactional
    public Optional<ConferenceSessionDTO> rejectSession(Long sessionId) {
        ConferenceSession session = conferenceSessionRepository.findById(sessionId);

        if (session != null) {
            session.reject();
            conferenceSessionRepository.persist(session);
            return Optional.of(new ConferenceSessionDTO(
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
                    session.getSpeakers().stream().map(spkr -> new SpeakerDTO(
                            spkr.getName(),
                            spkr.getEmail(),
                            spkr.getBio(),
                            spkr.getCompany(),
                            spkr.getTitle(),
                            spkr.getPhotoUrl()
                    )).collect(Collectors.toList())
            ));
        }
        return Optional.empty();
    }

    /**
     * Withdraws a session.
     *
     * @param sessionId the ID of the session to withdraw
     * @return an optional containing the updated session DTO if the session is found
     */
    @Transactional
    public Optional<ConferenceSessionDTO> withdrawSession(Long sessionId) {
        ConferenceSession session = conferenceSessionRepository.findById(sessionId);

        if (session != null) {
            session.withdraw();
            conferenceSessionRepository.persist(session);
            return Optional.of(new ConferenceSessionDTO(
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
                    session.getSpeakers().stream().map(spkr -> new SpeakerDTO(
                            spkr.getName(),
                            spkr.getEmail(),
                            spkr.getBio(),
                            spkr.getCompany(),
                            spkr.getTitle(),
                            spkr.getPhotoUrl()
                    )).collect(Collectors.toList())
            ));
        }
        return Optional.empty();
    }

    /**
     * Finds sessions by status.
     *
     * @param status the session status
     * @return a list of sessions with the given status
     */
    public List<ConferenceSessionDTO> findSessionsByStatus(ConferenceSession.SessionStatus status) {
        return conferenceSessionRepository.findByStatus(status).stream()
                .map(session -> new ConferenceSessionDTO(
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
                        session.getSpeakers().stream().map(spkr -> new SpeakerDTO(
                                spkr.getName(),
                                spkr.getEmail(),
                                spkr.getBio(),
                                spkr.getCompany(),
                                spkr.getTitle(),
                                spkr.getPhotoUrl()
                        )).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    /**
     * Finds sessions by speaker.
     *
     * @param speakerId the ID of the speaker
     * @return a list of sessions for the speaker
     */
    public List<ConferenceSessionDTO> findSessionsBySpeaker(Long speakerId) {
        return conferenceSessionRepository.findBySpeakerId(speakerId).stream()
                .map(session -> new ConferenceSessionDTO(
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
                        session.getSpeakers().stream().map(spkr -> new SpeakerDTO(
                                spkr.getName(),
                                spkr.getEmail(),
                                spkr.getBio(),
                                spkr.getCompany(),
                                spkr.getTitle(),
                                spkr.getPhotoUrl()
                        )).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}
