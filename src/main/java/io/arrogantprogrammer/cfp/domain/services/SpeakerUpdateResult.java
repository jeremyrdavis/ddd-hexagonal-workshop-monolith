package io.arrogantprogrammer.cfp.domain.services;

import io.arrogantprogrammer.cfp.domain.aggregates.Speaker;
import io.arrogantprogrammer.cfp.domain.events.SpeakerUpdatedEvent;

/**
 * Result object for speaker update operations.
 */
public record SpeakerUpdateResult(
    Speaker speaker,
    SpeakerUpdatedEvent event
) {
}
