package io.arrogantprogrammer.speakers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import io.quarkus.logging.Log;

@ApplicationScoped
public class SpeakerService {

    @Inject
    SpeakerRepository speakerRepository;

    @Inject
    SocialMediaRepository socialMediaRepository;

    public List<SpeakerEntity> getAllSpeakers() {
        return speakerRepository.listAll();
    }

    public Optional<SpeakerEntity> getSpeaker(Long id) {
        return speakerRepository.findByIdOptional(id);
    }

    @Transactional
    public SpeakerEntity createSpeaker(SpeakerEntity speakerEntity) {
        speakerRepository.persist(speakerEntity);
        return speakerEntity;
    }

    @Transactional
    public Optional<SpeakerEntity> updateSpeaker(Long id, SpeakerEntity speakerEntity) {
        Optional<SpeakerEntity> existing = speakerRepository.findByIdOptional(id);
        if(existing.isPresent()) {
            existing.get().name = speakerEntity.name;
            existing.get().title = speakerEntity.title;
            existing.get().company = speakerEntity.company;
            existing.get().bio = speakerEntity.bio;
            existing.get().headshot = speakerEntity.headshot;
            Log.debugf("Speaker updated: %s", existing.get());
            return existing;
        } else {
            Log.debugf("Speaker not found: %s", id);
            return Optional.empty();
        }

    //     return Optional.of(speakerRepository.findByIdOptional(id)
    //             .map(existing -> {
    //                 existing.name = speaker.name;
    //                 existing.title = speaker.title;
    //                 existing.company = speaker.company;
    //                 existing.bio = speaker.bio;
    //                 existing.headshot = speaker.headshot;
    //                 return existing;
    //             })
    //             .orElse(null));
     }

    @Transactional
    public boolean deleteSpeaker(Long id) {
        return speakerRepository.deleteById(id);
    }

    @Transactional
    public Optional<SpeakerEntity> addSocialMedia(Long id, SocialMedia socialMedia) {
        return speakerRepository.findByIdOptional(id)
                .map(speaker -> {
                    socialMedia.speakerEntity = speaker;
                    speaker.socialMedia.add(socialMedia);
                    socialMediaRepository.persist(socialMedia);
                    return speaker;
                });
    }

    @Transactional
    public Optional<SpeakerEntity> removeSocialMedia(Long id, Long socialId) {
        return speakerRepository.findByIdOptional(id)
                .map(speaker -> {
                    boolean removed = speaker.socialMedia.removeIf(sm -> sm.id.equals(socialId));
                    return removed ? speaker : null;
                })
                .filter(speaker -> speaker != null);
    }
} 