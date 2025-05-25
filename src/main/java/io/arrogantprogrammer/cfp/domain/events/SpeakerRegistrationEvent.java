package io.arrogantprogrammer.cfp.domain.events;

import io.arrogantprogrammer.domain.valueobjects.Email;

import java.time.Instant;

/**
 * Domain event that is raised when a speaker is registered.
 */
public class SpeakerRegistrationEvent {
    private final Email email;
    private final Instant timestamp;

    public SpeakerRegistrationEvent(Email email) {
        this.email = email;
        this.timestamp = Instant.now();
    }

    public Email getEmail() {
        return email;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
