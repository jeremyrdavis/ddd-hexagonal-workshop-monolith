package io.arrogantprogrammer.events;

import java.time.Instant;

public class AttendeeRegistered {
    private Long attendeeId;
    private String name;
    private String email;
    private String company;
    private String tshirtSize;
    private String dietaryPreference;
    private String socialMedia;
    private Instant timestamp;

    public AttendeeRegistered() {
    }

    public AttendeeRegistered(Long attendeeId, String name, String email, String company, 
                            String tshirtSize, String dietaryPreference, String socialMedia) {
        this.attendeeId = attendeeId;
        this.name = name;
        this.email = email;
        this.company = company;
        this.tshirtSize = tshirtSize;
        this.dietaryPreference = dietaryPreference;
        this.socialMedia = socialMedia;
        this.timestamp = Instant.now();
    }

    // Getters and Setters
    public Long getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(Long attendeeId) {
        this.attendeeId = attendeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTshirtSize() {
        return tshirtSize;
    }

    public void setTshirtSize(String tshirtSize) {
        this.tshirtSize = tshirtSize;
    }

    public String getDietaryPreference() {
        return dietaryPreference;
    }

    public void setDietaryPreference(String dietaryPreference) {
        this.dietaryPreference = dietaryPreference;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
} 