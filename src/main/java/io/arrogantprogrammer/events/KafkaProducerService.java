package io.arrogantprogrammer.events;

import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class KafkaProducerService {
    
    private static final Logger LOG = Logger.getLogger(KafkaProducerService.class);
    
    @Inject
    @Channel("attendee-registrations-outgoing")
    Emitter<Record<String, String>> emitter;

    public void publishEvent(String topic, String key, Object event) {
        try {
            String eventJson = EventSerializer.serialize(event);
            emitter.send(Record.of(key, eventJson))
                .whenComplete((success, failure) -> {
                    if (failure != null) {
                        LOG.errorf("Failed to publish event to topic %s: %s", topic, failure.getMessage());
                    } else {
                        LOG.infof("Successfully published event to topic %s with key %s", topic, key);
                    }
                });
        } catch (Exception e) {
            LOG.errorf("Error serializing event for topic %s: %s", topic, e.getMessage());
            throw new RuntimeException("Failed to publish event", e);
        }
    }
} 