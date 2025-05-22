package io.arrogantprogrammer.cfp;

import io.arrogantprogrammer.cfp.domain.services.CFPService;
import io.arrogantprogrammer.cfp.persistence.Speaker;
import io.arrogantprogrammer.cfp.persistence.SpeakerRepository;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class CFPServiceTestFindSpeakersTest {

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
    void setUp() {
        Mockito.when(speakerRepository.findByIdOptional(Mockito.any())).thenReturn(
               Optional.of(new Speaker(
                                name,
                                email,
                                bio,
                                company,
                                title,
                                photoUrl
                        )));
    }

    @Test
    void testFindSpeakerById() {

        Optional<SpeakerDTO> result = cfpService.getSpeaker(237L);

        // Assert
        assertTrue(result.isPresent());
        SpeakerDTO speakerDTO = result.get();
        assertEquals(name, speakerDTO.name());
        assertEquals(email, speakerDTO.email());
        assertEquals(bio, speakerDTO.bio());
        assertEquals(company, speakerDTO.company());
        assertEquals(title, speakerDTO.title());
        assertEquals(photoUrl, speakerDTO.photoUrl());
    }
}
