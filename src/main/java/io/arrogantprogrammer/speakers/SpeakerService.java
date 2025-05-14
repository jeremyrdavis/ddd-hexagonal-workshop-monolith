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

    public List<Speaker> getAllSpeakers() {
        return speakerRepository.listAll();
    }

    public Optional<Speaker> getSpeaker(Long id) {
        return speakerRepository.findByIdOptional(id);
    }

    @Transactional
    public Speaker createSpeaker(Speaker speaker) {
        speakerRepository.persist(speaker);
        return speaker;
    }

    @Transactional
    public Optional<Speaker> updateSpeaker(Long id, Speaker speaker) {
        Optional<Speaker> existing = speakerRepository.findByIdOptional(id);
        if(existing.isPresent()) {
            existing.get().name = speaker.name;
            existing.get().title = speaker.title;
            existing.get().company = speaker.company;
            existing.get().bio = speaker.bio;
            existing.get().headshot = speaker.headshot;
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
    public Optional<Speaker> addSocialMedia(Long id, SocialMedia socialMedia) {
        return speakerRepository.findByIdOptional(id)
                .map(speaker -> {
                    socialMedia.speaker = speaker;
                    speaker.socialMedia.add(socialMedia);
                    socialMediaRepository.persist(socialMedia);
                    return speaker;
                });
    }

    @Transactional
    public Optional<Speaker> removeSocialMedia(Long id, Long socialId) {
        return speakerRepository.findByIdOptional(id)
                .map(speaker -> {
                    boolean removed = speaker.socialMedia.removeIf(sm -> sm.id.equals(socialId));
                    return removed ? speaker : null;
                })
                .filter(speaker -> speaker != null);
    }
} 