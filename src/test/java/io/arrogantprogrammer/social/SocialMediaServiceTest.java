package io.arrogantprogrammer.social;

import io.arrogantprogrammer.events.AttendeeRegistered;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class SocialMediaServiceTest {
    
    @Inject
    SocialMediaService socialMediaService;
    
    @Test
    @Transactional
    public void testCreateWelcomePost() {
        // Given
        AttendeeRegistered event = new AttendeeRegistered(
            1L, "John Doe", "john@example.com", "Acme Corp",
            "L", "Vegetarian", "@johndoe"
        );
        
        // When
        SocialPost post = socialMediaService.createWelcomePost(event);
        
        // Then
        assertNotNull(post);
        assertEquals("Conference Bot", post.getAuthor());
        assertEquals("Twitter", post.getPlatform());
        assertEquals(1L, post.getAttendeeId());
        assertTrue(post.getContent().contains("John Doe"));
        assertTrue(post.getContent().contains("@johndoe"));
    }
    
    @Test
    @Transactional
    public void testGetRecentPosts() {
        // Given
        AttendeeRegistered event1 = new AttendeeRegistered(
            1L, "John Doe", "john@example.com", "Acme Corp",
            "L", "Vegetarian", "@johndoe"
        );
        AttendeeRegistered event2 = new AttendeeRegistered(
            2L, "Jane Smith", "jane@example.com", "XYZ Inc",
            "M", "Vegan", "@janesmith"
        );
        
        socialMediaService.createWelcomePost(event1);
        socialMediaService.createWelcomePost(event2);
        
        // When
        List<SocialPost> posts = socialMediaService.getRecentPosts();
        
        // Then
        assertEquals(2, posts.size());
        assertEquals("Jane Smith", posts.get(0).getContent().split(" ")[1]);
        assertEquals("John Doe", posts.get(1).getContent().split(" ")[1]);
    }
    
    @Test
    @Transactional
    public void testGetPostsByAttendee() {
        // Given
        AttendeeRegistered event = new AttendeeRegistered(
            1L, "John Doe", "john@example.com", "Acme Corp",
            "L", "Vegetarian", "@johndoe"
        );
        
        socialMediaService.createWelcomePost(event);
        
        // When
        List<SocialPost> posts = socialMediaService.getPostsByAttendee(1L);
        
        // Then
        assertEquals(1, posts.size());
        SocialPost post = posts.get(0);
        assertEquals("Conference Bot", post.getAuthor());
        assertEquals("Twitter", post.getPlatform());
        assertEquals(1L, post.getAttendeeId());
        assertTrue(post.getContent().contains("John Doe"));
        assertTrue(post.getContent().contains("@johndoe"));
    }
    
    @Test
    @Transactional
    public void testDeletePost() {
        // Given
        AttendeeRegistered event = new AttendeeRegistered(
            1L, "John Doe", "john@example.com", "Acme Corp",
            "L", "Vegetarian", "@johndoe"
        );
        
        SocialPost post = socialMediaService.createWelcomePost(event);
        
        // When
        socialMediaService.deletePost(post.getId());
        
        // Then
        assertNull(socialMediaService.getPost(post.getId()));
    }
} 