package io.arrogantprogrammer.cfp.domain.services;

        import io.arrogantprogrammer.cfp.ConferenceSessionRepository;
        import io.arrogantprogrammer.cfp.SpeakerDTO;
        import io.arrogantprogrammer.cfp.persistence.SpeakerEntity;
        import io.arrogantprogrammer.cfp.persistence.SpeakerRepository;
        import io.arrogantprogrammer.domain.valueobjects.Email;
        import io.arrogantprogrammer.domain.valueobjects.Name;
        import io.quarkus.test.InjectMock;
        import io.quarkus.test.junit.QuarkusMock;
        import io.quarkus.test.junit.QuarkusTest;
        import jakarta.inject.Inject;
        import org.junit.jupiter.api.Test;
        import org.mockito.Mockito;

        import java.util.List;

        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.junit.jupiter.api.Assertions.assertNotNull;
        import static org.junit.jupiter.api.Assertions.assertTrue;
        import static org.mockito.ArgumentMatchers.any;
        import static org.mockito.Mockito.times;
        import static org.mockito.Mockito.verify;

        @QuarkusTest
        public class CFPServiceTest {

            @Inject
            CFPService cfpService;

            @InjectMock
            SpeakerRepository speakerRepository;

            @InjectMock
            ConferenceSessionRepository conferenceSessionRepository;

            @Test
            void testRegisterSpeaker() {
                SpeakerDTO speakerDTO = new SpeakerDTO(
                        new Name("Bilbo", "Baggins"),
                        new Email("bilbo@theshire.com"), // email
                        "A respectable hobbit of the Shire, known for unexpected adventures and a remarkable journey to the Lonely Mountain.", // bio
                        "Test Company", // company
                        "Developer", // title
                        "https://example.com/photo.jpg" // headshot
                );
                SpeakerDTO result = cfpService.registerSpeaker(speakerDTO);
                assertNotNull(result);
                assertEquals(speakerDTO.name(), result.name());
                assertEquals(speakerDTO.email(), result.email());
                assertEquals(speakerDTO.bio(), result.bio());
                assertEquals(speakerDTO.company(), result.company());
                assertEquals(speakerDTO.title(), result.title());
                assertEquals(speakerDTO.headshot(), result.headshot());
                verify(speakerRepository, times(1)).persist(any(SpeakerEntity.class));
            }

            @Test
            void testFindByCompany() {
                // Create a mock repository
                SpeakerRepository mockRepo = Mockito.mock(SpeakerRepository.class);

                // Create test speakers
                List<SpeakerEntity> companySpeakerEntities = List.of(
                    SpeakerEntity.create(
                        new Name("John", "Doe"),
                        new Email("john@example.com"),
                        "John's Bio",
                        "Acme Inc",
                        "Developer",
                        "https://example.com/john.jpg"
                    ),
                    SpeakerEntity.create(
                        new Name("Jane", "Smith"),
                        new Email("jane@example.com"),
                        "Jane's Bio",
                        "Acme Inc",
                        "Designer",
                        "https://example.com/jane.jpg"
                    )
                );

                // Set up the mock
                Mockito.when(mockRepo.findByCompany("Acme Inc")).thenReturn(companySpeakerEntities);

                // Install the mock
                QuarkusMock.installMockForType(mockRepo, SpeakerRepository.class);

                // Call the method being tested
                var result = cfpService.findByCompany("Acme Inc");

                // Verify the result
                assertNotNull(result);
                assertEquals(2, result.size());
                assertEquals("John", result.get(0).name().getFirstName());
                assertEquals("Jane", result.get(1).name().getFirstName());
                assertEquals("Acme Inc", result.get(0).company());
                assertEquals("Acme Inc", result.get(1).company());
            }

        }