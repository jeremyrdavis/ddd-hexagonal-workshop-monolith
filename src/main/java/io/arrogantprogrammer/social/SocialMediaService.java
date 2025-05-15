package io.arrogantprogrammer.social;

import io.arrogantprogrammer.events.AttendeeRegistered;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class SocialMediaService {
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Transactional
    public SocialPost createWelcomePost(AttendeeRegistered event) {
        String content = generateWelcomeContent(event);
        SocialPost post = new SocialPost(
            content,
            "Conference Bot",
            "Twitter",
            event.getAttendeeId()
        );
        entityManager.persist(post);
        return post;
    }
    
    private String generateWelcomeContent(AttendeeRegistered event) {
        return String.format(
            "Welcome %s to our conference! We're excited to have you join us. " +
            "Don't forget to follow us for updates and connect with other attendees at %s",
            event.getName(),
            event.getSocialMedia()
        );
    }
    
    @Transactional
    public List<SocialPost> getRecentPosts() {
        return entityManager.createQuery(
            "SELECT p FROM SocialPost p ORDER BY p.createdAt DESC",
            SocialPost.class
        ).setMaxResults(10).getResultList();
    }
    
    @Transactional
    public List<SocialPost> getPostsByAttendee(Long attendeeId) {
        return entityManager.createQuery(
            "SELECT p FROM SocialPost p WHERE p.attendeeId = :attendeeId ORDER BY p.createdAt DESC",
            SocialPost.class
        ).setParameter("attendeeId", attendeeId).getResultList();
    }
    
    @Transactional
    public SocialPost getPost(Long id) {
        return entityManager.find(SocialPost.class, id);
    }
    
    @Transactional
    public void deletePost(Long id) {
        SocialPost post = getPost(id);
        if (post != null) {
            entityManager.remove(post);
        }
    }
} 