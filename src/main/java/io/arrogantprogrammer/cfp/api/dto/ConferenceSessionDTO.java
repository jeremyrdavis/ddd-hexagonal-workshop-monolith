package io.arrogantprogrammer.cfp.api.dto;

import io.arrogantprogrammer.cfp.domain.aggregates.ConferenceSession;

import java.util.List;

/**
 * Data Transfer Object for Conference Session information.
 */
public record ConferenceSessionDTO(
        Long id,
        String title,
        String summary,
        String outline,
        String learningObjectives,
        String targetAudience,
        String prerequisites,
        ConferenceSession.SessionType sessionType,
        ConferenceSession.SessionLevel sessionLevel,
        long durationMinutes,
        String status,
        List<SpeakerDTO> speakers
) {
}
