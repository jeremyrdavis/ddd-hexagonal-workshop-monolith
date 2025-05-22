package io.arrogantprogrammer.sharedkernel.events;

import io.arrogantprogrammer.domain.valueobjects.Email;

public record AbstractSubmittedEvent(Email email, String abstractTitle) {
}
