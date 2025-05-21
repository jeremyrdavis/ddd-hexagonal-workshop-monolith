package io.arrogantprogrammer.cfp;

import io.arrogantprogrammer.domain.valueobjects.Email;
import io.arrogantprogrammer.domain.valueobjects.Name;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing speakers.
 */
@ApplicationScoped
public class SpeakerService {
    
    @Inject
    SpeakerRepository speakerRepository;
    
    @Inject
    ConferenceSessionRepository sessionRepository;
    
    /**
     * Gets all speakers.
     * 
     * @return a list of all speakers
     */
    public List<Speaker> getAllSpeakers() {
        return speakerRepository.listAll();
    }
    
    /**
     * Gets a speaker by ID.
     * 
     * @param id the speaker ID
     * @return an optional containing the speaker if found
     */
    public Optional<Speaker> getSpeaker(Long id) {
        return speakerRepository.findByIdOptional(id);
    }
    
    /**
     * Creates a new speaker.
     * 
     * @param speaker the speaker to create
     * @return the created speaker
     */
    @Transactional
    public Speaker createSpeaker(Speaker speaker) {
        speakerRepository.persist(speaker);
        return speaker;
    }
    
    /**
     * Updates an existing speaker.
     * 
     * @param id the ID of the speaker to update
     * @param speaker the updated speaker data
     * @return an optional containing the updated speaker if found
     */
    @Transactional
    public Optional<Speaker> updateSpeaker(Long id, Speaker speaker) {
        return speakerRepository.findByIdOptional(id)
                .map(existing -> {
                    existing.updateName(speaker.getName());
                    existing.updateEmail(speaker.getEmail());
                    existing.updateBio(speaker.getBio());
                    existing.updateCompany(speaker.getCompany());
                    existing.updateTitle(speaker.getTitle());
                    existing.updatePhotoUrl(speaker.getPhotoUrl());
                    return existing;
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
        // First, remove the speaker from all sessions
        List<ConferenceSession> sessions = sessionRepository.findBySpeakerId(id);
        Optional<Speaker> speakerOpt = speakerRepository.findByIdOptional(id);
        
        if (speakerOpt.isPresent()) {
            Speaker speaker = speakerOpt.get();
            sessions.forEach(session -> session.removeSpeaker(speaker));
        }
        return speakerRepository.deleteById(id);
    }
    
    /**
     * Finds a speaker by email.
     * 
     * @param email the email to search for
     * @return an optional containing the speaker if found
     */
    public Optional<Speaker> findByEmail(String email) {
        return speakerRepository.findByEmail(email);
    }
    
    /**
     * Finds speakers by company.
     * 
     * @param company the company to search for
     * @return a list of speakers from the given company
     */
    public List<Speaker> findByCompany(String company) {
        return speakerRepository.findByCompany(company);
    }
    
    /**
     * Searches for speakers by name.
     * 
     * @param nameQuery the name query to search for
     * @return a list of speakers matching the name query
     */
    public List<Speaker> searchByName(String nameQuery) {
        return speakerRepository.searchByName(nameQuery);
    }
    
    /**
     * Creates a new speaker with the given details.
     * 
     * @param firstName the speaker's first name
     * @param lastName the speaker's last name
     * @param email the speaker's email
     * @param bio the speaker's bio
     * @param company the speaker's company
     * @param title the speaker's job title
     * @param photoUrl the URL to the speaker's photo
     * @return the created speaker
     */
    @Transactional
    public Speaker createSpeaker(String firstName, String lastName, String email, 
                               String bio, String company, String title, String photoUrl) {
        Speaker speaker = Speaker.create(new Name(firstName, lastName), new Email(email), bio, company, title, photoUrl);
        speakerRepository.persist(speaker);
        return speaker;
    }
    
    /**
     * Updates a speaker's name.
     * 
     * @param id the ID of the speaker to update
     * @param firstName the new first name
     * @param lastName the new last name
     * @return an optional containing the updated speaker if found
     */
    @Transactional
    public Optional<Speaker> updateName(Long id, String firstName, String lastName) {
        return speakerRepository.findByIdOptional(id)
                .map(speaker -> {
                    speaker.updateName(new Name(firstName, lastName));
                    return speaker;
                });
    }
    
    /**
     * Updates a speaker's email.
     * 
     * @param id the ID of the speaker to update
     * @param email the new email
     * @return an optional containing the updated speaker if found
     */
    @Transactional
    public Optional<Speaker> updateEmail(Long id, String email) {
        return speakerRepository.findByIdOptional(id)
                .map(speaker -> {
                    speaker.updateEmail(new Email(email));
                    return speaker;
                });
    }
}