package io.arrogantprogrammer.social;

import io.arrogantprogrammer.events.AttendeeRegistered;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class SocialMediaService {
    
    @Transactional
    public SocialPost createWelcomePost(AttendeeRegistered event) {
        String content = generateWelcomeContent(event);
        SocialPost post = new SocialPost(
            content,
            "Conference Bot",
            "Twitter",
            event.getAttendeeId()
        );
        post.persist();
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
        return SocialPost.find(
            "SELECT p FROM SocialPost p ORDER BY p.createdAt DESC").list();
    }
    
    @Transactional
    public List<SocialPost> getPostsByAttendee(Long attendeeId) {
        return SocialPost.find(
            "SELECT p FROM SocialPost p WHERE p.attendeeId = ?1 ORDER BY p.createdAt DESC",
            attendeeId).list();
    }
    
    @Transactional
    public SocialPost getPost(Long id) {
        return SocialPost.findById(id);
    }
    
    @Transactional
    public void deletePost(Long id) {
        SocialPost post = getPost(id);
        if (post != null) {
            SocialPost.deleteById(id);
        }
    }
} 