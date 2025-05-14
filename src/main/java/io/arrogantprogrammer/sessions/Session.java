package io.arrogantprogrammer.sessions;

import io.arrogantprogrammer.speakers.Speaker;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    public String title;

    public String description;

    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public String room;

    @ManyToMany
    @JoinTable(
        name = "session_speakers",
        joinColumns = @JoinColumn(name = "session_id"),
        inverseJoinColumns = @JoinColumn(name = "speaker_id")
    )
    public List<Speaker> speakers = new ArrayList<>();
} 