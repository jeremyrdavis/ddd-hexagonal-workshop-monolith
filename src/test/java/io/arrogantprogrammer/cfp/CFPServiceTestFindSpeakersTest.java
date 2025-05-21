package io.arrogantprogrammer.cfp;

import io.arrogantprogrammer.domain.valueobjects.Email;
import io.arrogantprogrammer.domain.valueobjects.Name;
import io.quarkus.test.junit.QuarkusMock;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ApplicationScoped
public class CFPServiceTestFindSpeakersTest {

    @Inject
    CFPService cfpService;

    @Inject
    SpeakerRepository speakerRepository;

    Name name = new Name("Frodo", "Baggins");
    Email email = new Email("frodo.baggins@shire.com");
    String bio = "A brave hobbit from the Shire who carried the One Ring to Mount Doom.";
    String company =  "The Fellowship of the Ring";
    String title =  "Ring Bearer";
    String photoUrl =  "http://example.com/frodo.jpg";

    @BeforeEach
    void setUp() {
        Mockito.mock(speakerRepository);
        Mockito.when(speakerRepository.findByIdOptional(Mockito.any())).thenReturn(
               Optional.of(new Speaker(
                                name,
                                email,
                                bio,
                                company,
                                title,
                                photoUrl
                        )));
        QuarkusMock.installMockForType(speakerRepository, SpeakerRepository.class);
    }

    @Test
    void testFindSpeakerById() {

        Optional<SpeakerDTO> result = cfpService.getSpeaker(237L);

        // Assert
        assertTrue(result.isPresent());
        SpeakerDTO speakerDTO = result.get();
        assertEquals(name, speakerDTO.name());
        assertEquals(email, speakerDTO.email());
        assertEquals("Bio", speakerDTO.bio());
        assertEquals("Company", speakerDTO.company());
        assertEquals("Title", speakerDTO.title());
        assertEquals("http://photo.url", speakerDTO.photoUrl());
    }
}
