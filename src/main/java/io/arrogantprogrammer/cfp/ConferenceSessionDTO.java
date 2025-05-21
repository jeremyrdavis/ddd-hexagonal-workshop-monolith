package io.arrogantprogrammer.cfp;

    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import java.time.Duration;
    import java.util.List;
    import java.util.ArrayList;

    /**
     * DTO for ConferenceSession.
     */
    public record ConferenceSessionDTO(
        Long id,

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Summary is required")
        String summary,

        String outline,

        String learningObjectives,

        String targetAudience,

        String prerequisites,

        @NotNull(message = "Session type is required")
        ConferenceSession.SessionType sessionType,

        @NotNull(message = "Session level is required")
        ConferenceSession.SessionLevel sessionLevel,

        @NotNull(message = "Duration is required")
        Long durationMinutes,

        String status,

        List<SpeakerDTO> speakers
    ) {
        public ConferenceSessionDTO(Long id, String title, String summary, String outline,
                                    String learningObjectives, String targetAudience, String prerequisites,
                                    ConferenceSession.SessionType sessionType, ConferenceSession.SessionLevel sessionLevel, Long durationMinutes,
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

        public ConferenceSessionDTO(String title, String summary, String outline, String learningObjectives, String targetAudience, String prerequisites, ConferenceSession.SessionType sessionType, ConferenceSession.SessionLevel sessionLevel, Long durationMinutes) {
            this(null, title, summary, outline, learningObjectives, targetAudience, prerequisites, sessionType, sessionLevel, durationMinutes, null, new ArrayList<>());
        }
    }