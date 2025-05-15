package io.arrogantprogrammer.catering;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

@ApplicationScoped
public class CateringEventConsumer {

    @Inject
    CateringService cateringService;

    @Incoming("attendee-registrations-catering")
    @Blocking
    public Uni<Void> consume(Message<String> message) {
        try {
            String eventJson = message.getPayload();
            Log.info("Received attendee registration event: " + eventJson);
            
            // Parse the event and create dietary requirement
            // This is a simplified example - you'll need to implement proper JSON parsing
            String preference = "Standard"; // Default preference
            String specialRequests = null;
            
            // Create dietary requirement
            cateringService.createDietaryRequirement(1L, preference, specialRequests);
            
            message.ack();
        } catch (Exception e) {
            Log.error("Error processing attendee registration event", e);
            message.nack(e);
        }
        return null;
    }
} 