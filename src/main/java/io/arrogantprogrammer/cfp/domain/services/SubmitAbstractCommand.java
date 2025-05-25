package io.arrogantprogrammer.cfp.domain.services;

import io.arrogantprogrammer.cfp.domain.valueobjects.SessionAbstract;
import io.arrogantprogrammer.domain.valueobjects.Email;

/**
 * Command object for submitting an abstract.
 */
public record SubmitAbstractCommand(
    Email email,
    SessionAbstract sessionAbstract
) {
}
