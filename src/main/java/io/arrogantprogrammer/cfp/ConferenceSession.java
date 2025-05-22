package io.arrogantprogrammer.cfp;

import io.arrogantprogrammer.cfp.persistence.SessionAbstractEntity;
import io.arrogantprogrammer.cfp.persistence.SpeakerEntity;
import jakarta.persistence.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ConferenceSession aggregate root that represents a session in the call for papers.
 * This is a Domain-Driven Design aggregate root.
 */
@Entity
@Table(name = "cfp_sessions")
public class ConferenceSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Embedded
    private SessionAbstractEntity sessionAbstractEntity;
    
    @Enumerated(EnumType.STRING)
    private SessionType sessionType;
    
    @Enumerated(EnumType.STRING)
    private SessionLevel sessionLevel;
    
    private Duration duration;
    
    @Enumerated(EnumType.STRING)
    private SessionStatus status;
    
    @ManyToMany
    @JoinTable(
        name = "cfp_session_speakers",
        joinColumns = @JoinColumn(name = "session_id"),
        inverseJoinColumns = @JoinColumn(name = "speaker_id")
    )
    private List<SpeakerEntity> speakerEntities = new ArrayList<>();
    
    /**
     * Required by JPA
     */
    protected ConferenceSession() {
    }
    
    /**
     * Creates a new ConferenceSession instance.
     * 
     * @param sessionAbstractEntity the session abstract
     * @param sessionType the type of session
     * @param sessionLevel the level of the session
     * @param duration the duration of the session
     */
    public ConferenceSession(SessionAbstractEntity sessionAbstractEntity, SessionType sessionType,
                             SessionLevel sessionLevel, Duration duration) {
        this.sessionAbstractEntity = Objects.requireNonNull(sessionAbstractEntity, "Session abstract cannot be null");
        this.sessionType = Objects.requireNonNull(sessionType, "Session type cannot be null");
        this.sessionLevel = Objects.requireNonNull(sessionLevel, "Session level cannot be null");
        this.duration = Objects.requireNonNull(duration, "Duration cannot be null");
        this.status = SessionStatus.SUBMITTED;
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
     * Gets the session abstract.
     * 
     * @return the session abstract
     */
    public SessionAbstractEntity getSessionAbstract() {
        return sessionAbstractEntity;
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
     * @return the list of speakers
     */
    public List<SpeakerEntity> getSpeakers() {
        return new ArrayList<>(speakerEntities);
    }
    
    /**
     * Updates the session abstract.
     * 
     * @param sessionAbstractEntity the new session abstract
     */
    public void updateSessionAbstract(SessionAbstractEntity sessionAbstractEntity) {
        this.sessionAbstractEntity = Objects.requireNonNull(sessionAbstractEntity, "Session abstract cannot be null");
    }
    
    /**
     * Updates the session type.
     * 
     * @param sessionType the new session type
     */
    public void updateSessionType(SessionType sessionType) {
        this.sessionType = Objects.requireNonNull(sessionType, "Session type cannot be null");
    }
    
    /**
     * Updates the session level.
     * 
     * @param sessionLevel the new session level
     */
    public void updateSessionLevel(SessionLevel sessionLevel) {
        this.sessionLevel = Objects.requireNonNull(sessionLevel, "Session level cannot be null");
    }
    
    /**
     * Updates the session duration.
     * 
     * @param duration the new duration
     */
    public void updateDuration(Duration duration) {
        this.duration = Objects.requireNonNull(duration, "Duration cannot be null");
    }
    
    /**
     * Updates the session status.
     * 
     * @param status the new status
     */
    public void updateStatus(SessionStatus status) {
        this.status = Objects.requireNonNull(status, "Status cannot be null");
    }
    
    /**
     * Adds a speaker to the session.
     * 
     * @param speakerEntity the speaker to add
     * @return true if the speaker was added, false if the speaker was already in the list
     */
    public boolean addSpeaker(SpeakerEntity speakerEntity) {
        Objects.requireNonNull(speakerEntity, "Speaker cannot be null");
        if (speakerEntities.contains(speakerEntity)) {
            return false;
        }
        return speakerEntities.add(speakerEntity);
    }
    
    /**
     * Removes a speaker from the session.
     * 
     * @param speakerEntity the speaker to remove
     * @return true if the speaker was removed, false if the speaker was not in the list
     */
    public boolean removeSpeaker(SpeakerEntity speakerEntity) {
        Objects.requireNonNull(speakerEntity, "Speaker cannot be null");
        return speakerEntities.remove(speakerEntity);
    }
    
    /**
     * Accepts the session.
     */
    public void accept() {
        this.status = SessionStatus.ACCEPTED;
    }
    
    /**
     * Rejects the session.
     */
    public void reject() {
        this.status = SessionStatus.REJECTED;
    }
    
    /**
     * Withdraws the session.
     */
    public void withdraw() {
        this.status = SessionStatus.WITHDRAWN;
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
                ", title='" + (sessionAbstractEntity != null ? sessionAbstractEntity.getTitle() : null) + '\'' +
                ", type=" + sessionType +
                ", level=" + sessionLevel +
                ", status=" + status +
                ", speakers=" + speakerEntities.size() +
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
}