package io.arrogantprogrammer.cfp;

import io.arrogantprogrammer.cfp.domain.services.CFPService;
import io.arrogantprogrammer.cfp.infrastructure.persistence.SpeakerEntity;
import io.arrogantprogrammer.cfp.infrastructure.persistence.SpeakerRepository;
import io.arrogantprogrammer.domain.valueobjects.Email;
import io.arrogantprogrammer.domain.valueobjects.Name;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class CFPUpdateSpeakerEntityTest {

    @Inject
    CFPService cfpService;

    @InjectMock
    SpeakerRepository speakerRepository;

    Name name = new Name("Frodo", "Baggins");
    Email email = new Email("frodo.baggins@shire.com");
    String bio = "A brave hobbit from the Shire who carried the One Ring to Mount Doom.";
    String company =  "The Fellowship of the Ring";
    String title =  "Ring Bearer";
    String photoUrl =  "http://example.com/frodo.jpg";

    @BeforeEach
    void  setup() {
        Mockito.when(speakerRepository.findByIdOptional(Mockito.any())).thenReturn(
        Optional.of(new SpeakerEntity(
                name,
                email,
                bio,
                company,
                title,
                photoUrl
        )));
    }

    @Test
    void testUpdateSpeaker() {
        // Arrange
        SpeakerDTO updatedSpeakerDTO = new SpeakerDTO(
                new Name("Frodo", "Baggins"),
                new Email("frodo.baggins@shire.com"),
                "A brave hobbit from the Shire who carried the One Ring to Mount Doom.",
                "The Fellowship of the Ring",
                "Ring Bearer",
                "http://theshire.uk/frodo.jpg");
        Mockito.when(speakerRepository.findByIdOptional(Mockito.any())).thenReturn(
                Optional.of(new SpeakerEntity(
                        updatedSpeakerDTO.name(),
                        updatedSpeakerDTO.email(),
                        updatedSpeakerDTO.bio(),
                        updatedSpeakerDTO.company(),
                        updatedSpeakerDTO.title(),
                        updatedSpeakerDTO.headshot()
                )));

        SpeakerDTO result = cfpService.updateSpeaker(237L, updatedSpeakerDTO).get();
        assertNotNull(result);
        assertEquals(updatedSpeakerDTO.bio(), result.bio());
        assertEquals(updatedSpeakerDTO.company(), result.company());
        assertEquals(updatedSpeakerDTO.title(), result.title());
        assertEquals(updatedSpeakerDTO.headshot(), result.headshot());
        assertEquals(updatedSpeakerDTO.name(), result.name());
        assertEquals(updatedSpeakerDTO.email(), result.email());
    }
}
