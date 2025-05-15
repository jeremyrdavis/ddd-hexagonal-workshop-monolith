package io.arrogantprogrammer.catering;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "dietary_requirements")
public class DietaryRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attendee_id")
    private Long attendeeId;

    @Column(nullable = false)
    private String preference;

    @Column(name = "special_requests")
    private String specialRequests;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    public DietaryRequirement() {
        this.createdAt = ZonedDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(Long attendeeId) {
        this.attendeeId = attendeeId;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
} 