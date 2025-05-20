package io.arrogantprogrammer.cfp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for ConferenceSession.
 */
public class ConferenceSessionDTO {
    public Long id;
    
    @NotBlank(message = "Title is required")
    public String title;
    
    @NotBlank(message = "Summary is required")
    public String summary;
    
    public String outline;
    
    public String learningObjectives;
    
    public String targetAudience;
    
    public String prerequisites;
    
    @NotNull(message = "Session type is required")
    public String sessionType;
    
    @NotNull(message = "Session level is required")
    public String sessionLevel;
    
    @NotNull(message = "Duration is required")
    public Long durationMinutes;
    
    public String status;
    
    public List<SpeakerDTO> speakers = new ArrayList<>();
    
    public ConferenceSessionDTO() {
    }
    
    public ConferenceSessionDTO(Long id, String title, String summary, String outline, 
                              String learningObjectives, String targetAudience, String prerequisites,
                              String sessionType, String sessionLevel, Long durationMinutes, 
                              String status, List<SpeakerDTO> speakers) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.outline = outline;
        this.learningObjectives = learningObjectives;
        this.targetAudience = targetAudience;
        this.prerequisites = prerequisites;
        this.sessionType = sessionType;
        this.sessionLevel = sessionLevel;
        this.durationMinutes = durationMinutes;
        this.status = status;
        this.speakers = speakers != null ? speakers : new ArrayList<>();
    }
}