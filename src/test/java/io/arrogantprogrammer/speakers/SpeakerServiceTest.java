package io.arrogantprogrammer.speakers;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
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
        SpeakerEntity speakerEntity = createTestSpeaker();
        List<SpeakerEntity> singleSpeakerEntityList = List.of(speakerEntity);
        Mockito.when(speakerRepository.listAll()).thenReturn(singleSpeakerEntityList);
        Optional<SpeakerEntity> optionalSpeaker = Optional.of(speakerEntity);
        Mockito.when(speakerRepository.findByIdOptional(1L)).thenReturn(optionalSpeaker);
        Optional<SpeakerEntity> emptySpeaker = Optional.empty();
        Mockito.when(speakerRepository.findByIdOptional(2L)).thenReturn(emptySpeaker);
        doAnswer(invocation -> {
            SpeakerEntity s = invocation.getArgument(0);
            return s;
        }).when(speakerRepository).persist(any(SpeakerEntity.class));
        SpeakerEntity existing = createTestSpeaker();
        existing.id = 3L;
        doAnswer(invocation -> {
            return Optional.of(existing);
        }).when(speakerRepository).findByIdOptional(3L);
        Mockito.when(speakerRepository.deleteById(1L)).thenReturn(true);



        QuarkusMock.installMockForType(speakerRepository, SpeakerRepository.class);
    }


    @Test
    void testGetAllSpeakers() {
        List<SpeakerEntity> result = service.getAllSpeakers();
        assertEquals(1, result.size());
    }

    @Test
    void testGetSpeaker() {
        Optional<SpeakerEntity> result = service.getSpeaker(1L);
        assertTrue(result.isPresent());
        assertEquals("Test Speaker", result.get().name);
    }

    @Test
    void testGetSpeakerNotFound() {
        Optional<SpeakerEntity> result = service.getSpeaker(2L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testCreateSpeaker() {
        SpeakerEntity speakerEntity = createTestSpeaker();
        SpeakerEntity result = service.createSpeaker(speakerEntity);
        assertEquals(speakerEntity, result);
    }

    @Test
    void testUpdateSpeaker() {
        SpeakerEntity updated = createTestSpeaker();
        updated.id = 3L;
        updated.name = "Updated Name";

        Optional<SpeakerEntity> result = service.updateSpeaker(3L, updated);

        assertTrue(result.isPresent());
        assertEquals(updated.name, result.get().name);
    }

    @Test
    void testUpdateSpeakerNotFound() {
        Optional<SpeakerEntity> result = service.updateSpeaker(2L, createTestSpeaker());
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
        SpeakerEntity speakerEntity = createTestSpeaker();
        SocialMedia socialMedia = new SocialMedia();
        socialMedia.platform = "Twitter";
        socialMedia.handle = "@test";
        when(speakerRepository.findByIdOptional(1L)).thenReturn(Optional.of(speakerEntity));
        doAnswer(invocation -> {
            SocialMedia sm = invocation.getArgument(0);
            return sm;
        }).when(socialMediaRepository).persist(any(SocialMedia.class));

        Optional<SpeakerEntity> result = service.addSocialMedia(1L, socialMedia);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().socialMedia.size());
        assertEquals(socialMedia, result.get().socialMedia.get(0));
    }

    @Test
    void testAddSocialMediaToNonExistentSpeaker() {
        SocialMedia socialMedia = new SocialMedia();
        when(speakerRepository.findByIdOptional(1L)).thenReturn(Optional.empty());

        Optional<SpeakerEntity> result = service.addSocialMedia(1L, socialMedia);

        assertTrue(result.isEmpty());
    }

    @Test
    void testRemoveSocialMedia() {
        SpeakerEntity speakerEntity = createTestSpeaker();
        SocialMedia socialMedia = new SocialMedia();
        socialMedia.id = 1L;
        speakerEntity.socialMedia.add(socialMedia);
        when(speakerRepository.findByIdOptional(1L)).thenReturn(Optional.of(speakerEntity));

        Optional<SpeakerEntity> result = service.removeSocialMedia(1L, 1L);

        assertTrue(result.isPresent());
        assertTrue(result.get().socialMedia.isEmpty());
    }

    @Test
    void testRemoveSocialMediaFromNonExistentSpeaker() {
        when(speakerRepository.findByIdOptional(1L)).thenReturn(Optional.empty());

        Optional<SpeakerEntity> result = service.removeSocialMedia(1L, 1L);

        assertTrue(result.isEmpty());
    }

    private SpeakerEntity createTestSpeaker() {
        SpeakerEntity speakerEntity = new SpeakerEntity();
        speakerEntity.name = "Test Speaker";
        speakerEntity.bio = "Test Bio";
        speakerEntity.headshot = "test.jpg";
        speakerEntity.socialMedia = new ArrayList<>();
        return speakerEntity;
    }
} 