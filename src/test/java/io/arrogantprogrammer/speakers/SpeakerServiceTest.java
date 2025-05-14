package io.arrogantprogrammer.speakers;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.ws.rs.WebApplicationException;
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

    @Test
    void testGetAllSpeakers() {
        List<Speaker> speakers = List.of(createTestSpeaker());
        when(speakerRepository.listAll()).thenReturn(speakers);

        List<Speaker> result = service.getAllSpeakers();

        assertEquals(speakers, result);
    }

    @Test
    void testGetSpeaker() {
        Speaker speaker = createTestSpeaker();
        when(speakerRepository.findByIdOptional(1L)).thenReturn(Optional.of(speaker));

        Optional<Speaker> result = service.getSpeaker(1L);

        assertTrue(result.isPresent());
        assertEquals(speaker, result.get());
    }

    @Test
    void testGetSpeakerNotFound() {
        when(speakerRepository.findByIdOptional(1L)).thenReturn(Optional.empty());

        Optional<Speaker> result = service.getSpeaker(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void testCreateSpeaker() {
        Speaker speaker = createTestSpeaker();
        doAnswer(invocation -> {
            Speaker s = invocation.getArgument(0);
            return s;
        }).when(speakerRepository).persist(any(Speaker.class));

        Speaker result = service.createSpeaker(speaker);

        assertEquals(speaker, result);
    }

    @Test
    void testUpdateSpeaker() {
        Speaker existing = createTestSpeaker();
        Speaker updated = createTestSpeaker();
        updated.name = "Updated Name";
        when(speakerRepository.findByIdOptional(1L)).thenReturn(Optional.of(existing));

        Optional<Speaker> result = service.updateSpeaker(1L, updated);

        assertTrue(result.isPresent());
        assertEquals(updated.name, result.get().name);
    }

    @Test
    void testUpdateSpeakerNotFound() {
        Speaker speaker = createTestSpeaker();
        when(speakerRepository.findByIdOptional(1L)).thenReturn(Optional.empty());

        Optional<Speaker> result = service.updateSpeaker(1L, speaker);

        assertTrue(result.isEmpty());
    }

    @Test
    void testDeleteSpeaker() {
        when(speakerRepository.deleteById(1L)).thenReturn(true);

        boolean result = service.deleteSpeaker(1L);

        assertTrue(result);
    }

    @Test
    void testDeleteSpeakerNotFound() {
        when(speakerRepository.deleteById(1L)).thenReturn(false);

        boolean result = service.deleteSpeaker(1L);

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