package io.arrogantprogrammer.sharedkernel.events;

import io.arrogantprogrammer.cfp.SpeakerDTO;
import io.arrogantprogrammer.domain.valueobjects.Email;

public record SpeakerUpdatedEvent(Email email){
}
