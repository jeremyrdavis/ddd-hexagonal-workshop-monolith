package io.arrogantprogrammer.social;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "social_posts")
public class SocialPost extends PanacheEntity {
    
    @Column(nullable = false)
    private String content;
    
    @Column(nullable = false)
    private String author;
    
    @Column(nullable = false)
    private String platform;
    
    @Column(name = "attendee_id", nullable = false)
    private Long attendeeId;
    
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;
    
    public SocialPost() {
        this.createdAt = ZonedDateTime.now();
    }
    
    public SocialPost(String content, String author, String platform, Long attendeeId) {
        this();
        this.content = content;
        this.author = author;
        this.platform = platform;
        this.attendeeId = attendeeId;
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getPlatform() {
        return platform;
    }
    
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    
    public Long getAttendeeId() {
        return attendeeId;
    }
    
    public void setAttendeeId(Long attendeeId) {
        this.attendeeId = attendeeId;
    }
    
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
} 