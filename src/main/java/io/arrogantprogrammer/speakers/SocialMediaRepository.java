package io.arrogantprogrammer.speakers;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SocialMediaRepository implements PanacheRepository<SocialMedia> {
} 