package io.arrogantprogrammer.speakers;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.WebApplicationException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
public class SpeakerServiceTest {

    @InjectMock
    SpeakerRepository speakerRepository;

    @InjectMock
    SocialMediaRepository socialMediaRepository;

    @Inject
    SpeakerService service;

    @BeforeEach
    public void setUp() {
        Speaker speaker = createTestSpeaker();
        List<Speaker> singleSpeakerList = List.of(speaker);
        Mockito.when(speakerRepository.listAll()).thenReturn(singleSpeakerList);
        Optional<Speaker> optionalSpeaker = Optional.of(speaker);
        Mockito.when(speakerRepository.findByIdOptional(1L)).thenReturn(optionalSpeaker);
        Optional<Speaker> emptySpeaker = Optional.empty();
        Mockito.when(speakerRepository.findByIdOptional(2L)).thenReturn(emptySpeaker);
        doAnswer(invocation -> {
            Speaker s = invocation.getArgument(0);
            return s;
        }).when(speakerRepository).persist(any(Speaker.class));
        Speaker existing = createTestSpeaker();
        existing.id = 3L;
        doAnswer(invocation -> {
            return Optional.of(existing);
        }).when(speakerRepository).findByIdOptional(3L);
        Mockito.when(speakerRepository.deleteById(1L)).thenReturn(true);



        QuarkusMock.installMockForType(speakerRepository, SpeakerRepository.class);
    }


    @Test
    void testGetAllSpeakers() {
        List<Speaker> result = service.getAllSpeakers();
        assertEquals(1, result.size());
    }

    @Test
    void testGetSpeaker() {
        Optional<Speaker> result = service.getSpeaker(1L);
        assertTrue(result.isPresent());
        assertEquals("Test Speaker", result.get().name);
    }

    @Test
    void testGetSpeakerNotFound() {
        Optional<Speaker> result = service.getSpeaker(2L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testCreateSpeaker() {
        Speaker speaker = createTestSpeaker();
        Speaker result = service.createSpeaker(speaker);
        assertEquals(speaker, result);
    }

    @Test
    void testUpdateSpeaker() {
        Speaker updated = createTestSpeaker();
        updated.id = 3L;
        updated.name = "Updated Name";

        Optional<Speaker> result = service.updateSpeaker(3L, updated);

        assertTrue(result.isPresent());
        assertEquals(updated.name, result.get().name);
    }

    @Test
    void testUpdateSpeakerNotFound() {
        Optional<Speaker> result = service.updateSpeaker(2L, createTestSpeaker());
        assertTrue(result.isEmpty());
    }

    @Test
    void testDeleteSpeaker() {
        boolean result = service.deleteSpeaker(1L);

        assertTrue(result);
    }

    @Test
    void testDeleteSpeakerNotFound() {
        boolean result = service.deleteSpeaker(2L);

        assertFalse(result);
    }

    @Test
    void testAddSocialMedia() {
        Speaker speaker = createTestSpeaker();
        SocialMedia socialMedia = new SocialMedia();
        socialMedia.platform = "Twitter";
        socialMedia.handle = "@test";
        when(speakerRepository.findByIdOptional(1L)).thenReturn(Optional.of(speaker));
        doAnswer(invocation -> {
            SocialMedia sm = invocation.getArgument(0);
            return sm;
        }).when(socialMediaRepository).persist(any(SocialMedia.class));

        Optional<Speaker> result = service.addSocialMedia(1L, socialMedia);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().socialMedia.size());
        assertEquals(socialMedia, result.get().socialMedia.get(0));
    }

    @Test
    void testAddSocialMediaToNonExistentSpeaker() {
        SocialMedia socialMedia = new SocialMedia();
        when(speakerRepository.findByIdOptional(1L)).thenReturn(Optional.empty());

        Optional<Speaker> result = service.addSocialMedia(1L, socialMedia);

        assertTrue(result.isEmpty());
    }

    @Test
    void testRemoveSocialMedia() {
        Speaker speaker = createTestSpeaker();
        SocialMedia socialMedia = new SocialMedia();
        socialMedia.id = 1L;
        speaker.socialMedia.add(socialMedia);
        when(speakerRepository.findByIdOptional(1L)).thenReturn(Optional.of(speaker));

        Optional<Speaker> result = service.removeSocialMedia(1L, 1L);

        assertTrue(result.isPresent());
        assertTrue(result.get().socialMedia.isEmpty());
    }

    @Test
    void testRemoveSocialMediaFromNonExistentSpeaker() {
        when(speakerRepository.findByIdOptional(1L)).thenReturn(Optional.empty());

        Optional<Speaker> result = service.removeSocialMedia(1L, 1L);

        assertTrue(result.isEmpty());
    }

    private Speaker createTestSpeaker() {
        Speaker speaker = new Speaker();
        speaker.name = "Test Speaker";
        speaker.bio = "Test Bio";
        speaker.headshot = "test.jpg";
        speaker.socialMedia = new ArrayList<>();
        return speaker;
    }
} 