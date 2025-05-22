package io.arrogantprogrammer.cfp.domain.aggregates;

import io.arrogantprogrammer.cfp.SpeakerDTO;
import io.arrogantprogrammer.cfp.domain.services.SpeakerRegistrationEvent;
import io.arrogantprogrammer.cfp.domain.services.SpeakerRegistrationResult;
import io.arrogantprogrammer.cfp.domain.services.SpeakerUpdateResult;
import io.arrogantprogrammer.cfp.domain.services.SubmitAbstractCommand;
import io.arrogantprogrammer.cfp.persistence.SpeakerEntity;
import io.arrogantprogrammer.sharedkernel.events.AbstractSubmittedEvent;
import io.arrogantprogrammer.sharedkernel.events.SpeakerUpdatedEvent;

/**
 * Aggregate root representing a speaker in the call for papers.
 */
public class Speaker {

    public static SpeakerRegistrationResult registerSpeaker(SpeakerDTO speakerDTO) {
        SpeakerEntity speakerEntity = new SpeakerEntity(
                speakerDTO.name(),
                speakerDTO.email(),
                speakerDTO.bio(),
                speakerDTO.company(),
                speakerDTO.title(),
                speakerDTO.headshot());
        SpeakerRegistrationEvent speakerRegistrationEvent = new SpeakerRegistrationEvent(speakerEntity.getEmail());
        SpeakerRegistrationResult speakerRegistrationResult = new SpeakerRegistrationResult(speakerEntity, speakerRegistrationEvent);
        return speakerRegistrationResult;
    }

    public static SpeakerUpdateResult updateSpeaker(SpeakerEntity speakerEntity, SpeakerDTO speakerDTO) {
        // Update the speaker entity with the new data
        speakerEntity.updateName(speakerDTO.name());
        speakerEntity.updateEmail(speakerDTO.email());
        speakerEntity.updateBio(speakerDTO.bio());
        speakerEntity.updateCompany(speakerDTO.company());
        speakerEntity.updateTitle(speakerDTO.title());
        speakerEntity.updatePhotoUrl(speakerDTO.headshot());
        SpeakerUpdatedEvent speakerUpdatedEvent = new SpeakerUpdatedEvent(speakerEntity.getEmail());
        SpeakerUpdateResult speakerUpdateResult = new SpeakerUpdateResult(speakerEntity, speakerUpdatedEvent);
        return speakerUpdateResult;
    }

    static AbstractSubmittedEvent submitAbstract(SubmitAbstractCommand submitAbstractCommand) {
        // Logic to handle the submission of an abstract
        // This is a placeholder for the actual implementation

        return new AbstractSubmittedEvent(submitAbstractCommand.email(), submitAbstractCommand.sessionAbstractEntity().getTitle());
    }


}
