package io.arrogantprogrammer.merchandise;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;
import io.smallrye.reactive.messaging.memory.InMemorySource;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.spi.Connector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
@QuarkusTestResource(KafkaTestResourceLifecycleManager.class)
class MerchandiseEventConsumerTest {

    @InjectSpy
    MerchandiseEventConsumer merchandiseEventConsumer;

    @Inject
    @Connector("smallrye-in-memory")
    InMemoryConnector connector;

    @AfterAll
    public static void clearChannels() {
        InMemoryConnector.clear();
    }

    @Test
    void testEventConsumption() {
        // Setup
        String eventJson = "{\"id\":1,\"name\":\"John Doe\",\"email\":\"john@example.com\"}";
        InMemorySource<String> source = connector.source("attendee-registrations-merchandise");

        // Send test event
        source.send(eventJson);

        // Verify service was called
        await().atMost(Duration.ofSeconds(5)).until(() -> {
            verify(merchandiseEventConsumer, times(1)).consume(any());
            return true;
        });
    }

//    @Test
//    void testErrorHandling() {
//        // Setup
//        String eventJson = "{\"id\":1,\"name\":\"John Doe\",\"email\":\"john@example.com\"}";
//        InMemorySource<String> source = connector.source("attendee-registrations-merchandise");
//
//        // Mock service to throw exception
//        doThrow(new RuntimeException("Test error"))
//            .when(merchandiseEventConsumer).createOrder(any(), any());
//
//        // Send test event
//        source.send(eventJson);
//
//        // Verify error was handled
//        await().atMost(Duration.ofSeconds(5)).until(() -> {
//            verify(merchandiseEventConsumer, times(1)).createOrder(any(), any());
//            return true;
//        });
//    }
} 