package com.conference.sessions;

import io.arrogantprogrammer.speakers.SpeakerDTO;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessionDTO {
    public Long id;
    
    @NotBlank(message = "Title is required")
    public String title;
    
    public String description;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public String room;
    public List<SpeakerDTO> speakers = new ArrayList<>();
} 