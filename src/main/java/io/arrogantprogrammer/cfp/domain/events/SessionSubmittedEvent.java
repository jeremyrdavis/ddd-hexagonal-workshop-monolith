package io.arrogantprogrammer.cfp.domain.events;

import io.arrogantprogrammer.cfp.domain.aggregates.ConferenceSession;

import java.time.Instant;

/**
 * Domain event that is raised when a session is submitted.
 */
public class SessionSubmittedEvent {
    private final String title;
    private final ConferenceSession.SessionType sessionType;
    private final ConferenceSession.SessionLevel sessionLevel;
    private final int durationMinutes;
    private final Instant timestamp;

    public SessionSubmittedEvent(String title, 
                                ConferenceSession.SessionType sessionType,
                                ConferenceSession.SessionLevel sessionLevel,
                                int durationMinutes) {
        this.title = title;
        this.sessionType = sessionType;
        this.sessionLevel = sessionLevel;
        this.durationMinutes = durationMinutes;
        this.timestamp = Instant.now();
    }

    public String getTitle() {
        return title;
    }

    public ConferenceSession.SessionType getSessionType() {
        return sessionType;
    }

    public ConferenceSession.SessionLevel getSessionLevel() {
        return sessionLevel;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
