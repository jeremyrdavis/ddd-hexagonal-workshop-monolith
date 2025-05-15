package io.arrogantprogrammer.events;

import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class KafkaConsumerService {
    
    private static final Logger LOG = Logger.getLogger(KafkaConsumerService.class);
    
//    @Incoming("attendee-registrations-incoming")
//    public void consume(Record<String, String> record) {
//        try {
//            String key = record.key();
//            String value = record.value();
//            LOG.infof("Received message with key %s", key);
//            processMessage(key, value);
//        } catch (Exception e) {
//            LOG.errorf("Error processing message: %s", e.getMessage());
//            handleError(e);
//        }
//    }
//
//    protected void processMessage(String key, String value){
//        Log.infof("Received event with key %s and value %s", key, value);
//    };
//
//    protected void handleError(Exception e) {
//        // Default implementation logs the error
//        LOG.errorf("Error processing message: %s", e.getMessage());
//    }
} 