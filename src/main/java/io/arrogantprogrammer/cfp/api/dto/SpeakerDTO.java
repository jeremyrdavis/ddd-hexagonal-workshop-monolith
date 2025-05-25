package io.arrogantprogrammer.cfp.api.dto;

import io.arrogantprogrammer.domain.valueobjects.Email;
import io.arrogantprogrammer.domain.valueobjects.Name;

/**
 * Data Transfer Object for Speaker information.
 */
public record SpeakerDTO(
        Name name,
        Email email,
        String bio,
        String company,
        String title,
        String headshot
) {
}
