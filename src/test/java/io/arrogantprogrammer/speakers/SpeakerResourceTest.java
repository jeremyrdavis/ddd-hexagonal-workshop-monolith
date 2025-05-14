package io.arrogantprogrammer.speakers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@QuarkusTest
class SpeakerResourceTest {

    @Test
    void testCreateAndGetSpeaker() {
        // Create a speaker
        Map<String, Object> speaker = new HashMap<>();
        speaker.put("name", "John Doe");
        speaker.put("title", "Senior Developer");
        speaker.put("company", "Tech Corp");
        speaker.put("bio", "Experienced developer");
        speaker.put("headshot", "http://example.com/photo.jpg");

        Long id = ((Number) given()
            .contentType(ContentType.JSON)
            .body(speaker)
            .when()
            .post("/api/speakers")
            .then()
            .statusCode(201)
            .extract()
            .path("id")).longValue();

        // Get the speaker
        given()
            .when()
            .get("/api/speakers/" + id)
            .then()
            .statusCode(200)
            .body("name", is("John Doe"))
            .body("title", is("Senior Developer"))
            .body("company", is("Tech Corp"))
            .body("bio", is("Experienced developer"))
            .body("headshot", is("http://example.com/photo.jpg"));
    }

    @Test
    void testGetAllSpeakers() {
        given()
            .when()
            .get("/api/speakers")
            .then()
            .statusCode(200)
            .body("$", notNullValue());
    }

    @Test
    void testUpdateSpeaker() {
        // Create a speaker first
        Map<String, Object> speaker = new HashMap<>();
        speaker.put("name", "John Doe");
        speaker.put("title", "Senior Developer");
        speaker.put("company", "Tech Corp");
        speaker.put("bio", "Experienced developer");
        speaker.put("headshot", "http://example.com/photo.jpg");

        Long id = ((Number) given()
            .contentType(ContentType.JSON)
            .body(speaker)
            .when()
            .post("/api/speakers")
            .then()
            .statusCode(201)
            .extract()
            .path("id")).longValue();

        // Update the speaker
        speaker.put("name", "John Updated");
        speaker.put("title", "Lead Developer");

        given()
            .contentType(ContentType.JSON)
            .body(speaker)
            .when()
            .put("/api/speakers/" + id)
            .then()
            .statusCode(200)
            .body("name", is("John Updated"))
            .body("title", is("Lead Developer"));
    }

    @Test
    void testDeleteSpeaker() {
        // Create a speaker first
        Map<String, Object> speaker = new HashMap<>();
        speaker.put("name", "John Doe");
        speaker.put("title", "Senior Developer");
        speaker.put("company", "Tech Corp");
        speaker.put("bio", "Experienced developer");
        speaker.put("headshot", "http://example.com/photo.jpg");

        Long id = ((Number) given()
            .contentType(ContentType.JSON)
            .body(speaker)
            .when()
            .post("/api/speakers")
            .then()
            .statusCode(201)
            .extract()
            .path("id")).longValue();

        // Delete the speaker
        given()
            .when()
            .delete("/api/speakers/" + id)
            .then()
            .statusCode(204);

        // Verify the speaker is deleted
        given()
            .when()
            .get("/api/speakers/" + id)
            .then()
            .statusCode(404);
    }

    @Test
    void testCreateSpeakerWithInvalidData() {
        Map<String, Object> speaker = new HashMap<>();
        // Missing required fields

        given()
            .contentType(ContentType.JSON)
            .body(speaker)
            .when()
            .post("/api/speakers")
            .then()
            .statusCode(400);
    }

    @Test
    void testGetNonExistentSpeaker() {
        given()
            .when()
            .get("/api/speakers/999")
            .then()
            .statusCode(404);
    }

    @Test
    void testAddSocialMedia() {
        // Create a speaker first
        Map<String, Object> speaker = new HashMap<>();
        speaker.put("name", "John Doe");
        speaker.put("title", "Senior Developer");
        speaker.put("company", "Tech Corp");
        speaker.put("bio", "Experienced developer");
        speaker.put("headshot", "http://example.com/photo.jpg");

        Long id = ((Number) given()
            .contentType(ContentType.JSON)
            .body(speaker)
            .when()
            .post("/api/speakers")
            .then()
            .statusCode(201)
            .extract()
            .path("id")).longValue();

        // Add social media
        Map<String, Object> socialMedia = new HashMap<>();
        socialMedia.put("platform", "Twitter");
        socialMedia.put("handle", "@johndoe");

        Long socialMediaId = ((Number) given()
            .contentType(ContentType.JSON)
            .body(socialMedia)
            .when()
            .post("/api/speakers/" + id + "/social")
            .then()
            .statusCode(201)
            .body("platform", is("Twitter"))
            .body("handle", is("@johndoe"))
            .extract()
            .path("id")).longValue();

        // Verify social media was added
        given()
            .when()
            .get("/api/speakers/" + id)
            .then()
            .statusCode(200)
            .body("socialMedia[0].platform", is("Twitter"))
            .body("socialMedia[0].handle", is("@johndoe"));
    }

    @Test
    void testRemoveSocialMedia() {
        // Create a speaker first
        Map<String, Object> speaker = new HashMap<>();
        speaker.put("name", "John Doe");
        speaker.put("title", "Senior Developer");
        speaker.put("company", "Tech Corp");
        speaker.put("bio", "Experienced developer");
        speaker.put("headshot", "http://example.com/photo.jpg");

        Long id = ((Number) given()
            .contentType(ContentType.JSON)
            .body(speaker)
            .when()
            .post("/api/speakers")
            .then()
            .statusCode(201)
            .extract()
            .path("id")).longValue();

        // Add social media
        Map<String, Object> socialMedia = new HashMap<>();
        socialMedia.put("platform", "Twitter");
        socialMedia.put("handle", "@johndoe");

        Long socialMediaId = ((Number) given()
            .contentType(ContentType.JSON)
            .body(socialMedia)
            .when()
            .post("/api/speakers/" + id + "/social")
            .then()
            .statusCode(201)
            .extract()
            .path("id")).longValue();

        // Remove social media
        given()
            .when()
            .delete("/api/speakers/" + id + "/social/" + socialMediaId)
            .then()
            .statusCode(204);

        // Verify social media was removed
        given()
            .when()
            .get("/api/speakers/" + id)
            .then()
            .statusCode(200)
            .body("socialMedia", hasSize(0));
    }

    @Test
    void testAddSocialMediaToNonExistentSpeaker() {
        Map<String, Object> socialMedia = new HashMap<>();
        socialMedia.put("platform", "Twitter");
        socialMedia.put("handle", "@johndoe");

        given()
            .contentType(ContentType.JSON)
            .body(socialMedia)
            .when()
            .post("/api/speakers/999/social")
            .then()
            .statusCode(404);
    }

    @Test
    void testRemoveNonExistentSocialMedia() {
        // Create a speaker first
        Map<String, Object> speaker = new HashMap<>();
        speaker.put("name", "John Doe");
        speaker.put("title", "Senior Developer");
        speaker.put("company", "Tech Corp");
        speaker.put("bio", "Experienced developer");
        speaker.put("headshot", "http://example.com/photo.jpg");

        Long id = ((Number) given()
            .contentType(ContentType.JSON)
            .body(speaker)
            .when()
            .post("/api/speakers")
            .then()
            .statusCode(201)
            .extract()
            .path("id")).longValue();

        given()
            .when()
            .delete("/api/speakers/" + id + "/social/999")
            .then()
            .statusCode(404);
    }
} 