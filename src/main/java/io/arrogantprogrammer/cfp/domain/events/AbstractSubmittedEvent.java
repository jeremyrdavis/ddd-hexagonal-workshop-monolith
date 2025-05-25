package io.arrogantprogrammer.cfp.domain.events;

import io.arrogantprogrammer.domain.valueobjects.Email;

import java.time.Instant;

/**
 * Domain event that is raised when an abstract is submitted.
 */
public class AbstractSubmittedEvent {
    private final Email email;
    private final String title;
    private final Instant timestamp;

    public AbstractSubmittedEvent(Email email, String title) {
        this.email = email;
        this.title = title;
        this.timestamp = Instant.now();
    }

    public Email getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
