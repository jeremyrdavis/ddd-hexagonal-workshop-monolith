package io.arrogantprogrammer.cfp.domain.services;

import io.arrogantprogrammer.cfp.domain.aggregates.Speaker;
import io.arrogantprogrammer.cfp.domain.events.AbstractSubmittedEvent;

/**
 * Result object for abstract submission operations.
 */
public record SubmitAbstractResult(
    Speaker speaker,
    AbstractSubmittedEvent event
) {
}
