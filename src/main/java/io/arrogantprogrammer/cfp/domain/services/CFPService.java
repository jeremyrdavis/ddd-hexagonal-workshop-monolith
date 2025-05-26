package io.arrogantprogrammer.cfp.domain.services;

import io.arrogantprogrammer.cfp.ConferenceSessionRepository;
import io.arrogantprogrammer.cfp.SpeakerDTO;
import io.arrogantprogrammer.cfp.domain.aggregates.Speaker;
import io.arrogantprogrammer.cfp.infrastructure.persistence.SpeakerRepository;
import io.arrogantprogrammer.cfp.infrastructure.persistence.SpeakerEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

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
    public SpeakerDTO registerSpeaker(SpeakerDTO speakerDTO) {
        SpeakerRegistrationResult speakerRegistrationResult = Speaker.registerSpeaker(speakerDTO);
        SpeakerEntity speakerEntity = speakerRegistrationResult.speakerEntity();
        speakerRepository.persist(speakerEntity);
        return new SpeakerDTO(
                speakerEntity.getName(),
                speakerEntity.getEmail(),
                speakerEntity.getBio(),
                speakerEntity.getCompany(),
                speakerEntity.getTitle(),
                speakerEntity.getPhotoUrl()
        );
    }

    public List<SpeakerDTO> getAllSpeakers() {
        return null;
    }

    public Optional<SpeakerDTO> getSpeaker(Long id) {
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
    public Optional<SpeakerDTO> updateSpeaker(long l, SpeakerDTO updatedSpeakerDTO) {
        SpeakerEntity speakerEntity = speakerRepository.findByIdOptional(l).get();
        if(speakerEntity != null) {
            SpeakerUpdateResult speakerUpdateResult = Speaker.updateSpeaker(speakerEntity, updatedSpeakerDTO);
            SpeakerEntity updatedSpeaker = speakerUpdateResult.speakerEntity();
            speakerRepository.persist(updatedSpeaker);
            return Optional.of(new SpeakerDTO(
                    updatedSpeaker.getName(),
                    updatedSpeaker.getEmail(),
                    updatedSpeaker.getBio(),
                    updatedSpeaker.getCompany(),
                    updatedSpeaker.getTitle(),
                    updatedSpeaker.getPhotoUrl()
            ));
        }
        return Optional.empty();
    }

    public boolean deleteSpeaker(final Long id) {
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

//
//    public Optional<ConferenceSessionDTO> getSession(Long id) {
//        return conferenceSessionRepository.findByIdOptional(id)
//                .map(session -> new ConferenceSessionDTO(
//                        session.getId(),
//                        session.getSessionAbstract().getTitle(),
//                        session.getSessionAbstract().getSummary(),
//                        session.getSessionAbstract().getOutline(),
//                        session.getSessionAbstract().getLearningObjectives(),
//                        session.getSessionAbstract().getTargetAudience(),
//                        session.getSessionAbstract().getPrerequisites(),
//                        session.getSessionType(),
//                        session.getSessionLevel(),
//                        session.getDuration().toMinutes(),
//                        session.getStatus().name(),
//                        session.getSpeakers().stream().map(speaker -> new SpeakerDTO(
//                                speaker.getName(),
//                                speaker.getEmail(),
//                                speaker.getBio(),
//                                speaker.getCompany(),
//                                speaker.getTitle(),
//                                speaker.getPhotoUrl()
//                        )).collect(Collectors.toList())
//                ));
//    }
//
//@Transactional
//public Optional<ConferenceSessionDTO> updateSession(long id, ConferenceSessionDTO updatedSession) {
//    ConferenceSession existingSession = conferenceSessionRepository.findById(id);
//    if (existingSession != null) {
//        existingSession.updateSessionAbstract(new SessionAbstract(updatedSession.title(), updatedSession.summary(), updatedSession.outline(), updatedSession.learningObjectives(), updatedSession.targetAudience(), updatedSession.prerequisites()));
//        existingSession.updateSessionType(updatedSession.sessionType());
//        existingSession.updateSessionLevel(updatedSession.sessionLevel());
//        existingSession.updateDuration(Duration.ofMinutes(updatedSession.durationMinutes()));
//        existingSession.updateStatus(ConferenceSession.SessionStatus.valueOf(updatedSession.status()));
//
//        conferenceSessionRepository.persist(existingSession);
//
//        return Optional.of(new ConferenceSessionDTO(
//                existingSession.getId(),
//                existingSession.getSessionAbstract().getTitle(),
//                existingSession.getSessionAbstract().getSummary(),
//                existingSession.getSessionAbstract().getOutline(),
//                existingSession.getSessionAbstract().getLearningObjectives(),
//                existingSession.getSessionAbstract().getTargetAudience(),
//                existingSession.getSessionAbstract().getPrerequisites(),
//                existingSession.getSessionType(),
//                existingSession.getSessionLevel(),
//                existingSession.getDuration().toMinutes(),
//                existingSession.getStatus().name(),
//                existingSession.getSpeakers().stream().map(speaker -> new SpeakerDTO(
//                        speaker.getName(),
//                        speaker.getEmail(),
//                        speaker.getBio(),
//                        speaker.getCompany(),
//                        speaker.getTitle(),
//                        speaker.getPhotoUrl()
//                )).collect(Collectors.toList())
//        ));
//    }
//    return Optional.empty();
//}
//
//    public ConferenceSessionDTO createSession(ConferenceSessionDTO dto) {
//        ConferenceSession session = new ConferenceSession(
//                new SessionAbstract(dto.title(), dto.summary(), dto.outline(), dto.learningObjectives(), dto.targetAudience(), dto.prerequisites()),
//                dto.sessionType(),
//                dto.sessionLevel(),
//                Duration.ofMinutes(dto.durationMinutes()));
//        conferenceSessionRepository.persist(session);
//        return new ConferenceSessionDTO(
//                session.getId(),
//                session.getSessionAbstract().getTitle(),
//                session.getSessionAbstract().getSummary(),
//                session.getSessionAbstract().getOutline(),
//                session.getSessionAbstract().getLearningObjectives(),
//                session.getSessionAbstract().getTargetAudience(),
//                session.getSessionAbstract().getPrerequisites(),
//                session.getSessionType(),
//                session.getSessionLevel(),
//                session.getDuration().toMinutes(),
//                session.getStatus().name(),
//                List.of()
//        );
//    }
//
//    public boolean deleteSession(Long id) {
//        if (conferenceSessionRepository.deleteById(id)) {
//            return true;
//        }
//        return false;
//    }
//
}
