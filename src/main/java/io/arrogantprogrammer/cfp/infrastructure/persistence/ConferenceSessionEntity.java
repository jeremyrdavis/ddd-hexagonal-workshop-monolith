package io.arrogantprogrammer.cfp.infrastructure.persistence;

import io.arrogantprogrammer.cfp.domain.aggregates.ConferenceSession;
import jakarta.persistence.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * JPA entity for ConferenceSession persistence.
 */
@Entity
@Table(name = "cfp_sessions")
public class ConferenceSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Embedded
    private SessionAbstractEntity sessionAbstract;
    
    @Enumerated(EnumType.STRING)
    private ConferenceSession.SessionType sessionType;
    
    @Enumerated(EnumType.STRING)
    private ConferenceSession.SessionLevel sessionLevel;
    
    private Duration duration;
    
    @Enumerated(EnumType.STRING)
    private ConferenceSession.SessionStatus status;
    
    @ManyToMany
    @JoinTable(
        name = "cfp_session_speakers",
        joinColumns = @JoinColumn(name = "session_id"),
        inverseJoinColumns = @JoinColumn(name = "speaker_id")
    )
    private List<SpeakerEntity> speakers = new ArrayList<>();
    
    /**
     * Required by JPA
     */
    public ConferenceSessionEntity() {
    }
    
    // Getters and setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public SessionAbstractEntity getSessionAbstract() {
        return sessionAbstract;
    }
    
    public void setSessionAbstract(SessionAbstractEntity sessionAbstract) {
        this.sessionAbstract = sessionAbstract;
    }
    
    public ConferenceSession.SessionType getSessionType() {
        return sessionType;
    }
    
    public void setSessionType(ConferenceSession.SessionType sessionType) {
        this.sessionType = sessionType;
    }
    
    public ConferenceSession.SessionLevel getSessionLevel() {
        return sessionLevel;
    }
    
    public void setSessionLevel(ConferenceSession.SessionLevel sessionLevel) {
        this.sessionLevel = sessionLevel;
    }
    
    public Duration getDuration() {
        return duration;
    }
    
    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    
    public ConferenceSession.SessionStatus getStatus() {
        return status;
    }
    
    public void setStatus(ConferenceSession.SessionStatus status) {
        this.status = status;
    }
    
    public List<SpeakerEntity> getSpeakers() {
        return speakers;
    }
    
    public void setSpeakers(List<SpeakerEntity> speakers) {
        this.speakers = speakers;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConferenceSessionEntity that = (ConferenceSessionEntity) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "ConferenceSessionEntity{" +
                "id=" + id +
                ", title='" + (sessionAbstract != null ? sessionAbstract.title : null) + '\'' +
                ", type=" + sessionType +
                ", level=" + sessionLevel +
                ", status=" + status +
                ", speakers=" + speakers.size() +
                '}';
    }
}
