package io.arrogantprogrammer.cfp.application;

import io.arrogantprogrammer.cfp.api.dto.SpeakerDTO;
import io.arrogantprogrammer.cfp.domain.aggregates.Speaker;
import io.arrogantprogrammer.cfp.domain.events.SpeakerRegistrationEvent;
import io.arrogantprogrammer.cfp.domain.events.SpeakerUpdatedEvent;
import io.arrogantprogrammer.cfp.domain.repositories.SpeakerRepository;
import io.arrogantprogrammer.cfp.domain.services.SpeakerRegistrationResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Application service for speaker management.
 */
@ApplicationScoped
public class SpeakerApplicationService {

    @Inject
    SpeakerRepository speakerRepository;

    /**
     * Registers a new speaker.
     * 
     * @param speakerDTO the speaker data
     * @return the registered speaker
     */
    @Transactional
    public SpeakerDTO registerSpeaker(SpeakerDTO speakerDTO) {
        SpeakerRegistrationResult result = Speaker.registerSpeaker(speakerDTO);
        Speaker speaker = speakerRepository.save(result.speaker());
        // Here you would publish the event: result.event()
        return mapToDTO(speaker);
    }

    /**
     * Gets all speakers.
     * 
     * @return a list of all speakers
     */
    public List<SpeakerDTO> getAllSpeakers() {
        return speakerRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Gets a speaker by ID.
     * 
     * @param id the speaker ID
     * @return an optional containing the speaker if found
     */
    public Optional<SpeakerDTO> getSpeaker(Long id) {
        return speakerRepository.findById(id).map(this::mapToDTO);
    }

    /**
     * Updates an existing speaker.
     * 
     * @param id the ID of the speaker to update
     * @param updatedSpeakerDTO the updated speaker data
     * @return an optional containing the updated speaker if found
     */
    @Transactional
    public Optional<SpeakerDTO> updateSpeaker(Long id, SpeakerDTO updatedSpeakerDTO) {
        return speakerRepository.findById(id).map(speaker -> {
            SpeakerUpdatedEvent event = speaker.update(updatedSpeakerDTO);
            Speaker updatedSpeaker = speakerRepository.save(speaker);
            // Here you would publish the event
            return mapToDTO(updatedSpeaker);
        });
    }

    /**
     * Deletes a speaker.
     * 
     * @param id the ID of the speaker to delete
     * @return true if the speaker was deleted, false if it was not found
     */
    @Transactional
    public boolean deleteSpeaker(Long id) {
        return speakerRepository.deleteById(id);
    }

    /**
     * Finds speakers by company.
     * 
     * @param company the company to search for
     * @return a list of speakers from the given company
     */
    public List<SpeakerDTO> findByCompany(String company) {
        return speakerRepository.findByCompany(company).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Searches for speakers by name.
     * 
     * @param nameQuery the name query to search for
     * @return a list of speakers matching the name query
     */
    public List<SpeakerDTO> searchByName(String nameQuery) {
        return speakerRepository.searchByName(nameQuery).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps a Speaker domain object to a SpeakerDTO.
     * 
     * @param speaker the speaker to map
     * @return the speaker DTO
     */
    private SpeakerDTO mapToDTO(Speaker speaker) {
        return new SpeakerDTO(
                speaker.getName(),
                speaker.getEmail(),
                speaker.getBio(),
                speaker.getCompany(),
                speaker.getTitle(),
                speaker.getPhotoUrl()
        );
    }
}
