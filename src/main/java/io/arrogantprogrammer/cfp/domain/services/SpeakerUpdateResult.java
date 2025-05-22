package io.arrogantprogrammer.cfp.domain.services;

import io.arrogantprogrammer.cfp.persistence.SpeakerEntity;
import io.arrogantprogrammer.sharedkernel.events.SpeakerUpdatedEvent;

public record SpeakerUpdateResult(SpeakerEntity speakerEntity, SpeakerUpdatedEvent speakerUpdatedEvent) {
}
