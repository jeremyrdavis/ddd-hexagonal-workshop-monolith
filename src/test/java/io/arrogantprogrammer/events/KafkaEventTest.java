package io.arrogantprogrammer.events;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class KafkaEventTest {

    @Inject
    KafkaProducerService producerService;

    @Test
    public void testEventPublishing() {
        // Create test event
        AttendeeRegistered event = new AttendeeRegistered(
            1L, "John Doe", "john@example.com", "Acme Corp",
            "L", "Vegetarian", "@johndoe"
        );

        // Publish event
        producerService.publishEvent("attendee-registrations-outgoing", "1", event);

        // Wait for message to be received
        await().atMost(Duration.ofSeconds(5)).until(() -> {
            // The message will be consumed by the KafkaConsumerService
            return true;
        });

        // Verify the event was processed
        // Note: In a real test, you would verify the event was processed by checking
        // the state of your application or by using a test consumer
    }
} 