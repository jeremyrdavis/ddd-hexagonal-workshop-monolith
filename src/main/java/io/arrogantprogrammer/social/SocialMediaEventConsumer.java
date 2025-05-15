package io.arrogantprogrammer.social;

import io.arrogantprogrammer.events.AttendeeRegistered;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;
import io.smallrye.reactive.messaging.kafka.Record;


@ApplicationScoped
public class SocialMediaEventConsumer {
    
    private static final Logger LOG = Logger.getLogger(SocialMediaEventConsumer.class);
    
    @Inject
    SocialMediaService socialMediaService;
    
    @Incoming("attendee-registrations-social")
    public void consume(Record<String, AttendeeRegistered> record) {
        try {
            AttendeeRegistered event = record.value();
            LOG.infof("Received attendee registration event for %s", event.getName());
            
            SocialPost post = socialMediaService.createWelcomePost(event);
            LOG.infof("Created welcome post for %s with id %d", event.getName(), post.getId());
        } catch (Exception e) {
            LOG.errorf("Error processing attendee registration event: %s", e.getMessage());
            throw new RuntimeException("Failed to process attendee registration event", e);
        }
    }
} 