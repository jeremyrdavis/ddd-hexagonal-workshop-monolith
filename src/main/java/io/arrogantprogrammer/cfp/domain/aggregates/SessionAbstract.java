package io.arrogantprogrammer.cfp.domain.aggregates;

import io.arrogantprogrammer.cfp.domain.services.SubmitAbstractCommand;
import io.arrogantprogrammer.sharedkernel.events.AbstractSubmittedEvent;

public class SessionAbstract {

    static AbstractSubmittedEvent submitAbstract(SubmitAbstractCommand submitAbstractCommand) {
        // Logic to handle the submission of an abstract
        // This is a placeholder for the actual implementation

        return new AbstractSubmittedEvent(submitAbstractCommand.email(), submitAbstractCommand.sessionAbstractEntity().getTitle());
    }
}
