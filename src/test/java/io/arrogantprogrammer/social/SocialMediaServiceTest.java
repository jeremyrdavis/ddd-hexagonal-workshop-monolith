package io.arrogantprogrammer.social;

    import io.arrogantprogrammer.events.AttendeeRegistered;
    import io.quarkus.test.junit.QuarkusTest;
    import jakarta.inject.Inject;
    import jakarta.transaction.Transactional;
    import org.junit.jupiter.api.MethodOrderer;
    import org.junit.jupiter.api.Order;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.TestMethodOrder;

    import java.util.List;

    import static org.junit.jupiter.api.Assertions.*;

    @QuarkusTest
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class SocialMediaServiceTest {

        @Inject
        SocialMediaService socialMediaService;

        @Test
        @Transactional
        @Order(1)
        public void testGetRecentPosts() {
            // Given
            AttendeeRegistered event1 = new AttendeeRegistered(
                    201L, "Bob Smith", "bob@example.com", "Tech Corp",
                    "L", "Vegetarian", "@bobsmith"
            );
            AttendeeRegistered event2 = new AttendeeRegistered(
                    202L, "Carol Davis", "carol@example.com", "XYZ Inc",
                    "M", "Vegan", "@caroldavis"
            );

            socialMediaService.createWelcomePost(event1);
            socialMediaService.createWelcomePost(event2);

            // When
            List<SocialPost> posts = socialMediaService.getRecentPosts();
            posts.stream().map(post -> post.getContent()).forEach(System.out::println);

            // Then
            assertEquals(2, posts.size());
            assertEquals("Carol", posts.get(0).getContent().split(" ")[1]);
            assertEquals("Bob", posts.get(1).getContent().split(" ")[1]);
        }

        @Test
        @Transactional
        @Order(2)
        public void testCreateWelcomePost() {
            // Given
            AttendeeRegistered event = new AttendeeRegistered(
                101L, "Alice Johnson", "alice@example.com", "Acme Corp",
                "L", "Vegetarian", "@alicejohnson"
            );

            // When
            SocialPost post = socialMediaService.createWelcomePost(event);

            // Then
            assertNotNull(post);
            assertEquals("Conference Bot", post.getAuthor());
            assertEquals("Twitter", post.getPlatform());
            assertEquals(101L, post.getAttendeeId());
            assertTrue(post.getContent().contains("Alice Johnson"));
            assertTrue(post.getContent().contains("@alicejohnson"));
        }

        @Test
        @Transactional
        @Order(3)
        public void testGetPostsByAttendee() {
            // Given
            AttendeeRegistered event = new AttendeeRegistered(
                301L, "David Wilson", "david@example.com", "ABC Corp",
                "L", "Vegetarian", "@davidwilson"
            );

            socialMediaService.createWelcomePost(event);

            // When
            List<SocialPost> posts = socialMediaService.getPostsByAttendee(301L);

            // Then
            assertEquals(1, posts.size());
            SocialPost post = posts.get(0);
            assertEquals("Conference Bot", post.getAuthor());
            assertEquals("Twitter", post.getPlatform());
            assertEquals(301L, post.getAttendeeId());
            assertTrue(post.getContent().contains("David Wilson"));
            assertTrue(post.getContent().contains("@davidwilson"));
        }

        @Test
        @Transactional
        @Order(4)
        public void testDeletePost() {
            // Given
            AttendeeRegistered event = new AttendeeRegistered(
                401L, "Emma Brown", "emma@example.com", "Global Inc",
                "L", "Vegetarian", "@emmabrown"
            );

            SocialPost post = socialMediaService.createWelcomePost(event);

            // When
            socialMediaService.deletePost(post.getId());

            // Then
            assertNull(socialMediaService.getPost(post.getId()));
        }
    }