package io.arrogantprogrammer.cfp.infrastructure.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Speaker entities.
 */
@ApplicationScoped
public class SpeakerRepository implements PanacheRepository<SpeakerEntity> {
    
    /**
     * Finds a speaker by email.
     * 
     * @param email the email to search for
     * @return an optional containing the speaker if found
     */
    public Optional<SpeakerEntity> findByEmail(String email) {
        return find("email.address", email).firstResultOptional();
    }
    
    /**
     * Finds speakers by company.
     * 
     * @param company the company to search for
     * @return a list of speakers from the given company
     */
    public List<SpeakerEntity> findByCompany(String company) {
        return find("company", company).list();
    }
    
    /**
     * Searches for speakers by name.
     * 
     * @param nameQuery the name query to search for
     * @return a list of speakers matching the name query
     */
    public List<SpeakerEntity> searchByName(String nameQuery) {
        return find("name.firstName LIKE ?1 OR name.lastName LIKE ?1", "%" + nameQuery + "%").list();
    }
}