package com.conference.agenda;

import java.time.LocalDateTime;
import java.util.List;

public class AgendaItem {
    public Long sessionId;
    public String title;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public String room;
    public List<AgendaSpeakerDTO> speakers;
    public boolean keynote;
    public boolean isBreak;
} 