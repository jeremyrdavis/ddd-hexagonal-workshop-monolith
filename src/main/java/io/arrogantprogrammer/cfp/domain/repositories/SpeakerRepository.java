package io.arrogantprogrammer.cfp.domain.repositories;

import io.arrogantprogrammer.cfp.domain.aggregates.Speaker;
import io.arrogantprogrammer.domain.valueobjects.Email;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Speaker aggregate.
 */
public interface SpeakerRepository {
    
    /**
     * Saves a speaker.
     * 
     * @param speaker the speaker to save
     * @return the saved speaker
     */
    Speaker save(Speaker speaker);
    
    /**
     * Finds a speaker by ID.
     * 
     * @param id the ID to search for
     * @return an optional containing the speaker if found
     */
    Optional<Speaker> findById(Long id);
    
    /**
     * Finds a speaker by email.
     * 
     * @param email the email to search for
     * @return an optional containing the speaker if found
     */
    Optional<Speaker> findByEmail(Email email);
    
    /**
     * Finds speakers by company.
     * 
     * @param company the company to search for
     * @return a list of speakers from the given company
     */
    List<Speaker> findByCompany(String company);
    
    /**
     * Searches for speakers by name.
     * 
     * @param nameQuery the name query to search for
     * @return a list of speakers matching the name query
     */
    List<Speaker> searchByName(String nameQuery);
    
    /**
     * Gets all speakers.
     * 
     * @return a list of all speakers
     */
    List<Speaker> findAll();
    
    /**
     * Deletes a speaker by ID.
     * 
     * @param id the ID of the speaker to delete
     * @return true if the speaker was deleted, false if it was not found
     */
    boolean deleteById(Long id);
}
