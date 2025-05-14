package io.arrogantprogrammer.speakers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
        return speakerRepository.findByIdOptional(id)
                .map(existing -> {
                    existing.name = speaker.name;
                    existing.title = speaker.title;
                    existing.company = speaker.company;
                    existing.bio = speaker.bio;
                    existing.headshot = speaker.headshot;
                    return existing;
                });
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