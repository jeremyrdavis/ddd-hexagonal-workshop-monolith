package io.arrogantprogrammer.social;

import io.arrogantprogrammer.events.AttendeeRegistered;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.smallrye.reactive.messaging.kafka.Record;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;
import io.smallrye.reactive.messaging.memory.InMemorySink;
import io.smallrye.reactive.messaging.memory.InMemorySource;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.spi.Connector;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
@QuarkusTestResource(KafkaTestResourceLifecycleManager.class)
public class SocialMediaEventConsumerTest2 {

    @Inject
    @Connector("smallrye-in-memory")
    InMemoryConnector connector;

    @InjectSpy
    SocialMediaEventConsumer socialMediaEventConsumer;

    @Test
    public void testProcessAttendeeRegistered() {

        InMemorySource<Record<String, AttendeeRegistered>> registrationsSource = connector.source("attendee-registrations-social");
        AttendeeRegistered event = new AttendeeRegistered(
                1L, "John Doe", "john@example.com", "Acme Corp",
                "L", "Vegetarian", "@johndoe"
        );
        registrationsSource.send(Record.of("1", event));
        Mockito.verify(socialMediaEventConsumer, Mockito.times(1)).consume(any(Record.class));

        // This test will be run in the context of the KafkaTestResourceLifecycleManager
        // and will use the InMemoryConnector for testing.
        // You can add your test logic here.
    }


}
