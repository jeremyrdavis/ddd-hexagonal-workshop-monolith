package io.arrogantprogrammer.cfp.domain.services;

import io.arrogantprogrammer.cfp.persistence.SpeakerEntity;
import io.arrogantprogrammer.sharedkernel.events.AbstractSubmittedEvent;

public record SubmitAbstractResult(SpeakerEntity speakerEntity, AbstractSubmittedEvent abstractSubmittedEvent) {
}
