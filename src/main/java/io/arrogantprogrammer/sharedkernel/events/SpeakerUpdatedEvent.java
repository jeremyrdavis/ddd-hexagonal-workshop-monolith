package io.arrogantprogrammer.sharedkernel.events;

import io.arrogantprogrammer.cfp.SpeakerDTO;
import io.arrogantprogrammer.domain.valueobjects.Email;

public record SpeakerUpdatedEvent(EventType eventType, Email email){

    public SpeakerUpdatedEvent(Email email) {
        this(EventType.SPEAKER_UPDATED, email);
    }
}
