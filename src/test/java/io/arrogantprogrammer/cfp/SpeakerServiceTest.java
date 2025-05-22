package io.arrogantprogrammer.cfp;

import io.arrogantprogrammer.cfp.persistence.Speaker;
import io.arrogantprogrammer.cfp.persistence.SpeakerRepository;
import io.arrogantprogrammer.domain.valueobjects.Email;
import io.arrogantprogrammer.domain.valueobjects.Name;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
public class SpeakerServiceTest {

    @Inject
    SpeakerService speakerService;

    @InjectMock
    SpeakerRepository speakerRepository;

    @InjectMock
    ConferenceSessionRepository sessionRepository;

    private Speaker speaker;

    @BeforeEach
    void setUp() {
        // Create a speaker for testing
        speaker = Speaker.create(
                new Name("John", "Doe"),
                new Email("johndoe@something.com"),
                "Speaker bio",
                "Example Corp",
                "Developer",
                "https://example.com/photo.jpg"
        );
        
        // Set an ID for the speaker
        Mockito.when(speakerRepository.findByIdOptional(1L)).thenReturn(Optional.of(speaker));
        Mockito.when(speakerRepository.findByIdOptional(99L)).thenReturn(Optional.empty());
        
        // Mock persist method
        doAnswer(invocation -> {
            Speaker s = invocation.getArgument(0);
            return s;
        }).when(speakerRepository).persist(any(Speaker.class));
        
        // Mock deleteById method
        Mockito.when(speakerRepository.deleteById(1L)).thenReturn(true);
        Mockito.when(speakerRepository.deleteById(99L)).thenReturn(false);
        
        // Install mocks
        QuarkusMock.installMockForType(speakerRepository, SpeakerRepository.class);
        QuarkusMock.installMockForType(sessionRepository, ConferenceSessionRepository.class);
    }

    @Test
    void getAllSpeakers() {
        // Arrange
        List<Speaker> expectedSpeakers = Arrays.asList(speaker);
        when(speakerRepository.listAll()).thenReturn(expectedSpeakers);

        // Act
        List<Speaker> actualSpeakers = speakerService.getAllSpeakers();

        // Assert
        assertEquals(expectedSpeakers, actualSpeakers);
        verify(speakerRepository).listAll();
    }

    @Test
    void getSpeaker() {
        // Act
        Optional<Speaker> result = speakerService.getSpeaker(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(speaker, result.get());
        verify(speakerRepository).findByIdOptional(1L);
    }

    @Test
    void getSpeakerNotFound() {
        // Act
        Optional<Speaker> result = speakerService.getSpeaker(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(speakerRepository).findByIdOptional(99L);
    }

    @Test
    void createSpeaker() {
        // Act
        Speaker result = speakerService.createSpeaker(speaker);

        // Assert
        assertEquals(speaker, result);
        verify(speakerRepository).persist(speaker);
    }

    @Test
    void updateSpeaker() {
        // Arrange
        Speaker updatedSpeaker = Speaker.create(
                new Name("Jane", "Smith"),
                new Email("jane.smith@example.com"),
                "Updated bio",
                "New Corp",
                "Manager",
                "https://example.com/new-photo.jpg"
        );

        // Act
        Optional<Speaker> result = speakerService.updateSpeaker(1L, updatedSpeaker);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(updatedSpeaker.getName(), result.get().getName());
        assertEquals(updatedSpeaker.getEmail(), result.get().getEmail());
        assertEquals(updatedSpeaker.getBio(), result.get().getBio());
        assertEquals(updatedSpeaker.getCompany(), result.get().getCompany());
        assertEquals(updatedSpeaker.getTitle(), result.get().getTitle());
        assertEquals(updatedSpeaker.getPhotoUrl(), result.get().getPhotoUrl());
        verify(speakerRepository).findByIdOptional(1L);
    }

    @Test
    void updateSpeakerNotFound() {
        // Arrange
        Speaker updatedSpeaker = Speaker.create(
                new Name("Jane","Smith"),
                new Email("jane.smith@example.com"),
                "Updated bio",
                "New Corp",
                "Manager",
                "https://example.com/new-photo.jpg"
        );

        // Act
        Optional<Speaker> result = speakerService.updateSpeaker(99L, updatedSpeaker);

        // Assert
        assertFalse(result.isPresent());
        verify(speakerRepository).findByIdOptional(99L);
    }

    @Test
    void deleteSpeaker() {
        // Arrange
        List<ConferenceSession> sessions = List.of();
        when(sessionRepository.findBySpeakerId(1L)).thenReturn(sessions);

        // Act
        boolean result = speakerService.deleteSpeaker(1L);

        // Assert
        assertTrue(result);
        verify(speakerRepository).deleteById(1L);
        verify(sessionRepository).findBySpeakerId(1L);
    }

    @Test
    void deleteSpeakerWithSessions() {
        // Arrange
        ConferenceSession session = mock(ConferenceSession.class);
        List<ConferenceSession> sessions = List.of(session);
        when(sessionRepository.findBySpeakerId(1L)).thenReturn(sessions);

        // Act
        boolean result = speakerService.deleteSpeaker(1L);

        // Assert
        assertTrue(result);
        verify(speakerRepository).findByIdOptional(1L);
        verify(sessionRepository).findBySpeakerId(1L);
        verify(session).removeSpeaker(speaker);
        verify(speakerRepository).deleteById(1L);
    }

    @Test
    void deleteSpeakerNotFound() {
        // Act
        boolean result = speakerService.deleteSpeaker(99L);

        // Assert
        assertFalse(result);
        verify(speakerRepository).deleteById(99L);
    }

    @Test
    void findByEmail() {
        // Arrange
        when(speakerRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(speaker));

        // Act
        Optional<Speaker> result = speakerService.findByEmail("john.doe@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(speaker, result.get());
        verify(speakerRepository).findByEmail("john.doe@example.com");
    }

    @Test
    void findByCompany() {
        // Arrange
        List<Speaker> expectedSpeakers = Arrays.asList(speaker);
        when(speakerRepository.findByCompany("Example Corp")).thenReturn(expectedSpeakers);

        // Act
        List<Speaker> result = speakerService.findByCompany("Example Corp");

        // Assert
        assertEquals(expectedSpeakers, result);
        verify(speakerRepository).findByCompany("Example Corp");
    }

    @Test
    void searchByName() {
        // Arrange
        List<Speaker> expectedSpeakers = Arrays.asList(speaker);
        when(speakerRepository.searchByName("John")).thenReturn(expectedSpeakers);

        // Act
        List<Speaker> result = speakerService.searchByName("John");

        // Assert
        assertEquals(expectedSpeakers, result);
        verify(speakerRepository).searchByName("John");
    }

    @Test
    void createSpeakerWithDetails() {
        // Arrange
        String firstName = "Jane";
        String lastName = "Smith";
        String email = "jane.smith@example.com";
        String bio = "Test bio";
        String company = "Test Corp";
        String title = "Tester";
        String photoUrl = "https://example.com/test.jpg";
        
        Speaker newSpeaker = Speaker.create(new Name(firstName, lastName), new Email(email), bio, company, title, photoUrl);
        
        // Act
        Speaker result = speakerService.createSpeaker(firstName, lastName, email, bio, company, title, photoUrl);
        
        // Assert
        assertEquals(newSpeaker.getName().getFirstName(), result.getName().getFirstName());
        assertEquals(newSpeaker.getName().getLastName(), result.getName().getLastName());
        assertEquals(newSpeaker.getEmail().getValue(), result.getEmail().getValue());
        assertEquals(newSpeaker.getBio(), result.getBio());
        assertEquals(newSpeaker.getCompany(), result.getCompany());
        assertEquals(newSpeaker.getTitle(), result.getTitle());
        assertEquals(newSpeaker.getPhotoUrl(), result.getPhotoUrl());
        verify(speakerRepository).persist(any(Speaker.class));
    }

    @Test
    void updateName() {
        // Arrange
        String firstName = "Jane";
        String lastName = "Smith";
        
        // Act
        Optional<Speaker> result = speakerService.updateName(1L, firstName, lastName);
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals(firstName, result.get().getName().getFirstName());
        assertEquals(lastName, result.get().getName().getLastName());
        verify(speakerRepository).findByIdOptional(1L);
    }

    @Test
    void updateEmail() {
        // Arrange
        String email = "new.email@example.com";
        
        // Act
        Optional<Speaker> result = speakerService.updateEmail(1L, email);
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail().getValue());
        verify(speakerRepository).findByIdOptional(1L);
    }
}