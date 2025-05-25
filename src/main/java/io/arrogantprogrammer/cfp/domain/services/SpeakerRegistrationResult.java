package io.arrogantprogrammer.cfp.domain.services;

import io.arrogantprogrammer.cfp.domain.aggregates.Speaker;
import io.arrogantprogrammer.cfp.domain.events.SpeakerRegistrationEvent;

/**
 * Result object for speaker registration operations.
 */
public record SpeakerRegistrationResult(
    Speaker speaker,
    SpeakerRegistrationEvent event
) {
}
