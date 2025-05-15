package io.arrogantprogrammer.social;

import io.arrogantprogrammer.events.AttendeeRegistered;
import io.arrogantprogrammer.events.EventSerializer;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;
import io.smallrye.reactive.messaging.memory.InMemorySink;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import java.time.Duration;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
public class SocialMediaEventConsumerTest {
    
    @InjectMock
    SocialMediaService socialMediaService;
    
    @Inject @Any
    InMemoryConnector connector;
    
    @Inject
    @Channel("attendee-registrations-incoming")
    Emitter<Record<String, String>> emitter;

    @BeforeEach
    public void setup() {
        connector.clear();
        InMemoryConnector.switchIncomingChannelsToInMemory("attendee-registrations-incoming");
    }

    @Test
    public void testProcessAttendeeRegistered() {
        // Given
        AttendeeRegistered event = new AttendeeRegistered(
            1L, "John Doe", "john@example.com", "Acme Corp",
            "L", "Vegetarian", "@johndoe"
        );
        
        // When
        String eventJson = EventSerializer.serialize(event);
        emitter.send(Record.of("1", eventJson));
        
        // Then
        await().atMost(Duration.ofSeconds(5)).until(() -> {
            List<SocialPost> posts = socialMediaService.getPostsByAttendee(1L);
            return posts.size() == 1;
        });
        
        List<SocialPost> posts = socialMediaService.getPostsByAttendee(1L);
        assertEquals(1, posts.size());
        SocialPost post = posts.get(0);
        assertEquals("Conference Bot", post.getAuthor());
        assertEquals("Twitter", post.getPlatform());
        assertTrue(post.getContent().contains("John Doe"));
        assertTrue(post.getContent().contains("@johndoe"));
    }
    
    @Test
    public void testErrorHandling() {
        // Given
        Mockito.doThrow(new RuntimeException("Test error"))
            .when(socialMediaService).createWelcomePost(any());
        
        AttendeeRegistered event = new AttendeeRegistered(
            1L, "John Doe", "john@example.com", "Acme Corp",
            "L", "Vegetarian", "@johndoe"
        );
        
        // When/Then
        String eventJson = EventSerializer.serialize(event);
        assertThrows(RuntimeException.class, () -> {
            emitter.send(Record.of("1", eventJson));
        });
    }
} 