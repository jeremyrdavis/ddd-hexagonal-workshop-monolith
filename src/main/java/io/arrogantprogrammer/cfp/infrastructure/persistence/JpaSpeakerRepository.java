package io.arrogantprogrammer.cfp.infrastructure.persistence;

import io.arrogantprogrammer.cfp.domain.aggregates.Speaker;
import io.arrogantprogrammer.cfp.domain.repositories.SpeakerRepository;
import io.arrogantprogrammer.domain.valueobjects.Email;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JPA implementation of the SpeakerRepository interface.
 */
@ApplicationScoped
public class JpaSpeakerRepository implements SpeakerRepository, PanacheRepository<SpeakerEntity> {
    
    @Inject
    EntityManager entityManager;
    
    @Override
    public Speaker save(Speaker speaker) {
        SpeakerEntity entity = mapToEntity(speaker);
        if (entity.getId() == null) {
            persist(entity);
        } else {
            entity = getEntityManager().merge(entity);
        }
        return mapToDomain(entity);
    }
    
    @Override
    public Optional<Speaker> findById(Long id) {
        return findByIdOptional(id).map(this::mapToDomain);
    }
    
    @Override
    public Optional<Speaker> findByEmail(Email email) {
        return find("email.address", email.value()).firstResultOptional().map(this::mapToDomain);
    }
    
    @Override
    public List<Speaker> findByCompany(String company) {
        return find("company", company).list().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Speaker> searchByName(String nameQuery) {
        return find("name.firstName LIKE ?1 OR name.lastName LIKE ?1", "%" + nameQuery + "%").list().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Speaker> findAll() {
        return listAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
    
    /**
     * Maps a Speaker domain object to a SpeakerEntity.
     * 
     * @param speaker the speaker to map
     * @return the speaker entity
     */
    private SpeakerEntity mapToEntity(Speaker speaker) {
        SpeakerEntity entity = new SpeakerEntity();
        entity.setId(speaker.getId());
        entity.setName(speaker.getName());
        entity.setEmail(speaker.getEmail());
        entity.setBio(speaker.getBio());
        entity.setCompany(speaker.getCompany());
        entity.setTitle(speaker.getTitle());
        entity.setPhotoUrl(speaker.getPhotoUrl());
        return entity;
    }
    
    /**
     * Maps a SpeakerEntity to a Speaker domain object.
     * 
     * @param entity the entity to map
     * @return the speaker domain object
     */
    public Speaker mapToDomain(SpeakerEntity entity) {
        Speaker speaker = new Speaker(
                entity.getName(),
                entity.getEmail(),
                entity.getBio(),
                entity.getCompany(),
                entity.getTitle(),
                entity.getPhotoUrl()
        );
        speaker.setId(entity.getId());
        return speaker;
    }
}
