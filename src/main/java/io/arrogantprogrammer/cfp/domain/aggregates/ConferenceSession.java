package io.arrogantprogrammer.cfp.domain.aggregates;

import io.arrogantprogrammer.cfp.domain.events.SessionAcceptedEvent;
import io.arrogantprogrammer.cfp.domain.events.SessionRejectedEvent;
import io.arrogantprogrammer.cfp.domain.events.SessionSubmittedEvent;
import io.arrogantprogrammer.cfp.domain.events.SessionWithdrawnEvent;
import io.arrogantprogrammer.cfp.domain.valueobjects.SessionAbstract;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * ConferenceSession aggregate root that represents a session in the call for papers.
 * This is a Domain-Driven Design aggregate root.
 */
public class ConferenceSession {
    private Long id;
    private SessionAbstract sessionAbstract;
    private SessionType sessionType;
    private SessionLevel sessionLevel;
    private Duration duration;
    private SessionStatus status;
    private final List<Speaker> speakers = new ArrayList<>();
    private final Instant createdAt;
    private Instant lastModifiedAt;

    /**
     * Creates a new ConferenceSession instance.
     *
     * @param sessionAbstract the session abstract
     * @param sessionType the type of session
     * @param sessionLevel the level of the session
     * @param duration the duration of the session
     */
    public ConferenceSession(SessionAbstract sessionAbstract, SessionType sessionType,
                             SessionLevel sessionLevel, Duration duration) {
        this.sessionAbstract = Objects.requireNonNull(sessionAbstract, "Session abstract cannot be null");
        this.sessionType = Objects.requireNonNull(sessionType, "Session type cannot be null");
        this.sessionLevel = Objects.requireNonNull(sessionLevel, "Session level cannot be null");
        this.duration = Objects.requireNonNull(duration, "Duration cannot be null");
        this.status = SessionStatus.SUBMITTED;
        this.createdAt = Instant.now();
        this.lastModifiedAt = this.createdAt;
    }

    /**
     * Factory method to create a new conference session.
     *
     * @param sessionAbstract the session abstract
     * @param sessionType the type of session
     * @param sessionLevel the level of the session
     * @param durationMinutes the duration of the session in minutes
     * @return a new ConferenceSession instance and a domain event
     */
    public static SessionSubmissionResult submit(SessionAbstract sessionAbstract,
                                                 SessionType sessionType,
                                                 SessionLevel sessionLevel,
                                                 int durationMinutes) {
        ConferenceSession session = new ConferenceSession(
                sessionAbstract,
                sessionType,
                sessionLevel,
                Duration.ofMinutes(durationMinutes)
        );

        SessionSubmittedEvent event = new SessionSubmittedEvent(
                sessionAbstract.title(),
                sessionType,
                sessionLevel,
                durationMinutes
        );

        return new SessionSubmissionResult(session, event);
    }

    /**
     * Gets the session ID.
     *
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the session ID (for persistence purposes).
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the session abstract.
     *
     * @return the session abstract
     */
    public SessionAbstract getSessionAbstract() {
        return sessionAbstract;
    }

    /**
     * Gets the session type.
     *
     * @return the session type
     */
    public SessionType getSessionType() {
        return sessionType;
    }

    /**
     * Gets the session level.
     *
     * @return the session level
     */
    public SessionLevel getSessionLevel() {
        return sessionLevel;
    }

    /**
     * Gets the session duration.
     *
     * @return the duration
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Gets the session status.
     *
     * @return the status
     */
    public SessionStatus getStatus() {
        return status;
    }

    /**
     * Gets the session speakers.
     *
     * @return an unmodifiable list of speakers
     */
    public List<Speaker> getSpeakers() {
        return Collections.unmodifiableList(speakers);
    }

    /**
     * Gets the creation timestamp.
     *
     * @return the creation timestamp
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the last modified timestamp.
     *
     * @return the last modified timestamp
     */
    public Instant getLastModifiedAt() {
        return lastModifiedAt;
    }

    /**
     * Updates the session abstract.
     *
     * @param sessionAbstract the new session abstract
     */
    public void updateSessionAbstract(SessionAbstract sessionAbstract) {
        this.sessionAbstract = Objects.requireNonNull(sessionAbstract, "Session abstract cannot be null");
        this.lastModifiedAt = Instant.now();
    }

    /**
     * Updates the session type.
     *
     * @param sessionType the new session type
     */
    public void updateSessionType(SessionType sessionType) {
        this.sessionType = Objects.requireNonNull(sessionType, "Session type cannot be null");
        this.lastModifiedAt = Instant.now();
    }

    /**
     * Updates the session level.
     *
     * @param sessionLevel the new session level
     */
    public void updateSessionLevel(SessionLevel sessionLevel) {
        this.sessionLevel = Objects.requireNonNull(sessionLevel, "Session level cannot be null");
        this.lastModifiedAt = Instant.now();
    }

    /**
     * Updates the session duration.
     *
     * @param duration the new duration
     */
    public void updateDuration(Duration duration) {
        this.duration = Objects.requireNonNull(duration, "Duration cannot be null");
        this.lastModifiedAt = Instant.now();
    }

    /**
     * Updates the session status.
     *
     * @param status the new status
     */
    public void updateStatus(SessionStatus status) {
        this.status = Objects.requireNonNull(status, "Status cannot be null");
        this.lastModifiedAt = Instant.now();
    }

    /**
     * Adds a speaker to the session.
     *
     * @param speaker the speaker to add
     * @return true if the speaker was added, false if the speaker was already in the list
     */
    public boolean addSpeaker(Speaker speaker) {
        Objects.requireNonNull(speaker, "Speaker cannot be null");
        if (speakers.contains(speaker)) {
            return false;
        }
        boolean added = speakers.add(speaker);
        if (added) {
            this.lastModifiedAt = Instant.now();
        }
        return added;
    }

    /**
     * Removes a speaker from the session.
     *
     * @param speaker the speaker to remove
     * @return true if the speaker was removed, false if the speaker was not in the list
     */
    public boolean removeSpeaker(Speaker speaker) {
        Objects.requireNonNull(speaker, "Speaker cannot be null");
        boolean removed = speakers.remove(speaker);
        if (removed) {
            this.lastModifiedAt = Instant.now();
        }
        return removed;
    }

    /**
     * Accepts the session.
     *
     * @return the domain event for the acceptance
     */
    public SessionAcceptedEvent accept() {
        if (this.status == SessionStatus.ACCEPTED) {
            throw new IllegalStateException("Session is already accepted");
        }
        this.status = SessionStatus.ACCEPTED;
        this.lastModifiedAt = Instant.now();
        return new SessionAcceptedEvent(this.id, this.sessionAbstract.title());
    }

    /**
     * Rejects the session.
     *
     * @return the domain event for the rejection
     */
    public SessionRejectedEvent reject() {
        if (this.status == SessionStatus.REJECTED) {
            throw new IllegalStateException("Session is already rejected");
        }
        this.status = SessionStatus.REJECTED;
        this.lastModifiedAt = Instant.now();
        return new SessionRejectedEvent(this.id, this.sessionAbstract.title());
    }

    /**
     * Withdraws the session.
     *
     * @return the domain event for the withdrawal
     */
    public SessionWithdrawnEvent withdraw() {
        if (this.status == SessionStatus.WITHDRAWN) {
            throw new IllegalStateException("Session is already withdrawn");
        }
        this.status = SessionStatus.WITHDRAWN;
        this.lastModifiedAt = Instant.now();
        return new SessionWithdrawnEvent(this.id, this.sessionAbstract.title());
    }

    /**
     * Puts the session under review.
     */
    public void markUnderReview() {
        if (this.status != SessionStatus.SUBMITTED) {
            throw new IllegalStateException("Only submitted sessions can be put under review");
        }
        this.status = SessionStatus.UNDER_REVIEW;
        this.lastModifiedAt = Instant.now();
    }

    /**
     * Checks if the session is eligible for review.
     *
     * @return true if the session is eligible for review
     */
    public boolean isEligibleForReview() {
        return this.status == SessionStatus.SUBMITTED && !speakers.isEmpty();
    }

    /**
     * Checks if the session has been finalized (accepted, rejected, or withdrawn).
     *
     * @return true if the session has been finalized
     */
    public boolean isFinalized() {
        return this.status == SessionStatus.ACCEPTED ||
                this.status == SessionStatus.REJECTED ||
                this.status == SessionStatus.WITHDRAWN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConferenceSession that = (ConferenceSession) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ConferenceSession{" +
                "id=" + id +
                ", title='" + (sessionAbstract != null ? sessionAbstract.title() : null) + '\'' +
                ", type=" + sessionType +
                ", level=" + sessionLevel +
                ", status=" + status +
                ", speakers=" + speakers.size() +
                '}';
    }

    /**
     * Session type enum.
     */
    public enum SessionType {
        KEYNOTE,
        TALK,
        WORKSHOP,
        PANEL,
        LIGHTNING_TALK,
        BOF
    }

    /**
     * Session level enum.
     */
    public enum SessionLevel {
        BEGINNER,
        INTERMEDIATE,
        ADVANCED,
        EXPERT
    }

    /**
     * Session status enum.
     */
    public enum SessionStatus {
        SUBMITTED,
        UNDER_REVIEW,
        ACCEPTED,
        REJECTED,
        WITHDRAWN
    }

    /**
     * Result class for session submission.
     */
    public record SessionSubmissionResult(ConferenceSession session, SessionSubmittedEvent event) {}
}