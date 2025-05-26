package io.arrogantprogrammer.cfp.domain.aggregates;

import io.arrogantprogrammer.cfp.domain.services.SubmitAbstractCommand;
import io.arrogantprogrammer.cfp.domain.services.SubmitAbstractResult;
import io.arrogantprogrammer.cfp.domain.valueobjects.SessionAbstract;
import io.arrogantprogrammer.cfp.infrastructure.persistence.SpeakerEntity;
import io.arrogantprogrammer.domain.valueobjects.Email;
import io.arrogantprogrammer.domain.valueobjects.Name;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SessionAbstractTest {

    @Test
    public void testSubmitAbstract() {
        Email email = new Email("gandalf@fotr.org");
        // Create a new speaker entity
        SpeakerEntity speakerEntity = new SpeakerEntity(
                Name.fromFullName("Gandalf the Grey"),
                email,
                "A wizard of Middle-earth",
                "The Fellowship of the Ring",
                "Senior Wizard",
                "http://example.com/gandalf.jpg"
        );
        SessionAbstract sessionAbstract = new SessionAbstract(
                "The One Ring",
                "A deep dive into the lore of the One Ring",
                "There and back again with magic.",
                "Your first spell.",
                "Aspiring wizards",
                "A staff"
        );
        SubmitAbstractCommand submitAbstractCommand = new SubmitAbstractCommand(email, sessionAbstract);

        SubmitAbstractResult submitAbstractResult = Speaker.submitAbstract(speakerEntity, submitAbstractCommand);
        assertNotNull(submitAbstractResult);
        assertNotNull(submitAbstractResult.speakerEntity());
        assertNotNull(submitAbstractResult.speakerEntity().getSessionAbstracts());
        assertNotNull(submitAbstractResult.abstractSubmittedEvent());
        assertEquals(email, submitAbstractResult.abstractSubmittedEvent().email());
    }
}
