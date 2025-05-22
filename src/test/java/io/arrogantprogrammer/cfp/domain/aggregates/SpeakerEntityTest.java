package io.arrogantprogrammer.cfp.domain.aggregates;

import io.arrogantprogrammer.cfp.SpeakerDTO;
import io.arrogantprogrammer.cfp.domain.services.SpeakerRegistrationResult;
import io.arrogantprogrammer.cfp.domain.services.SpeakerUpdateResult;
import io.arrogantprogrammer.cfp.persistence.SpeakerEntity;
import io.arrogantprogrammer.domain.valueobjects.Email;
import io.arrogantprogrammer.domain.valueobjects.Name;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpeakerEntityTest {

    @Test
    void testUpdateSpeaker() {
        SpeakerEntity speakerEntity = new SpeakerEntity(
                new Name("Old", "Name"),
                new Email("old.email@example.com"),
                "Old Title",
                "Old Company",
                "Old Bio",
                "Old Headshot"
        );
        Name nameDTO = new Name("New", "Name");
        Email newEmail = new Email("new.email@example.com");
        SpeakerDTO speakerDTO = new SpeakerDTO(nameDTO, newEmail, "New Bio", "New Company", "New Title", "New Headshot");

        SpeakerUpdateResult result = Speaker.updateSpeaker(speakerEntity, speakerDTO);

        assertNotNull(result);
        assertEquals("New Name", speakerEntity.getName().getFullName());
        assertEquals("new.email@example.com", speakerEntity.getEmail().getValue());
        assertEquals("New Bio", speakerEntity.getBio());
        assertEquals("New Company", speakerEntity.getCompany());
        assertEquals("New Title", speakerEntity.getTitle());
        assertEquals("New Headshot", speakerEntity.getPhotoUrl());
        assertEquals("new.email@example.com", result.speakerUpdatedEvent().email().getValue());
    }

    @Test
    void testRegisterSpeaker() {
        Name nameDTO = new Name("Test", "Speaker");
        Email email = new Email("test.speaker@example.com");
        SpeakerDTO speakerDTO = new SpeakerDTO(nameDTO, email, "Test Bio", "Test Company", "Test Title", "Test Headshot");

        SpeakerRegistrationResult result = Speaker.registerSpeaker(speakerDTO);

        assertNotNull(result);
        SpeakerEntity speakerEntity = result.speakerEntity();
        assertEquals("Test Speaker", speakerEntity.getName().getFullName());
        assertEquals("test.speaker@example.com", speakerEntity.getEmail().getValue());
        assertEquals("Test Title", speakerEntity.getTitle());
        assertEquals("Test Company", speakerEntity.getCompany());
        assertEquals("Test Bio", speakerEntity.getBio());
        assertEquals("Test Headshot", speakerEntity.getPhotoUrl());
    }
}