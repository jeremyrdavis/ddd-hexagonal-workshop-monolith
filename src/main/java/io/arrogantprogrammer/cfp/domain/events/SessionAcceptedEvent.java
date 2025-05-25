package io.arrogantprogrammer.cfp.domain.events;

import java.time.Instant;

/**
 * Domain event that is raised when a session is accepted.
 */
public class SessionAcceptedEvent {
    private final Long sessionId;
    private final String title;
    private final Instant timestamp;

    public SessionAcceptedEvent(Long sessionId, String title) {
        this.sessionId = sessionId;
        this.title = title;
        this.timestamp = Instant.now();
    }

    public Long getSessionId() {
        return sessionId;
    }

    public String getTitle() {
        return title;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
