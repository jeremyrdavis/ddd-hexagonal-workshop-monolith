package io.arrogantprogrammer.cfp.domain.aggregates;

import io.arrogantprogrammer.cfp.domain.services.SubmitAbstractCommand;
import io.arrogantprogrammer.cfp.persistence.SessionAbstractEntity;
import io.arrogantprogrammer.domain.valueobjects.Email;
import io.arrogantprogrammer.sharedkernel.events.AbstractSubmittedEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SessionAbstractTest {

    @Test
    public void testSubmitAbstract() {
        Email email = new Email("gandalf@fotr.org");
        SessionAbstractEntity sessionAbstractEntity = new SessionAbstractEntity(
                "The One Ring",
                "A deep dive into the lore of the One Ring",
                "There and back again with magic.",
                "Your first spell.",
                "Aspiring wizards",
                "A staff"
        );
        SubmitAbstractCommand submitAbstractCommand = new SubmitAbstractCommand(email, sessionAbstractEntity);

        AbstractSubmittedEvent abstractSubmittedEvent = Speaker.submitAbstract(submitAbstractCommand);
        assertNotNull(abstractSubmittedEvent);
    }
}
