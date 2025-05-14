package io.arrogantprogrammer.speakers;

import com.conference.sessions.Session;
import io.restassured.http.ContentType;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class SessionResourceTest {

    @Test
    void testCreateAndGetSession() {
        Map<String, Object> session = new HashMap<>();
        session.put("title", "Keynote");
        session.put("description", "Opening keynote");
        session.put("room", "Main Hall");
        session.put("startTime", "2025-05-14T09:00:00");
        session.put("endTime", "2025-05-14T10:00:00");

        Long id = ((Number) given()
            .contentType(ContentType.JSON)
            .body(session)
            .when()
            .post("/api/sessions")
            .then()
            .statusCode(201)
            .extract()
            .path("id")).longValue();

        given()
            .when()
            .get("/api/sessions/" + id)
            .then()
            .statusCode(200)
            .body("title", is("Keynote"))
            .body("room", is("Main Hall"));
    }

    @Test
    void testUpdateSession() {
        Map<String, Object> session = new HashMap<>();
        session.put("title", "Session A");
        session.put("room", "Room 1");
        Long id = ((Number) given()
            .contentType(ContentType.JSON)
            .body(session)
            .when()
            .post("/api/sessions")
            .then()
            .statusCode(201)
            .extract()
            .path("id")).longValue();

        session.put("title", "Session A Updated");
        session.put("room", "Room 2");
        given()
            .contentType(ContentType.JSON)
            .body(session)
            .when()
            .put("/api/sessions/" + id)
            .then()
            .statusCode(200)
            .body("title", is("Session A Updated"))
            .body("room", is("Room 2"));
    }

    @Test
    void testDeleteSession() {
        Map<String, Object> session = new HashMap<>();
        session.put("title", "Session B");
        Long id = ((Number) given()
            .contentType(ContentType.JSON)
            .body(session)
            .when()
            .post("/api/sessions")
            .then()
            .statusCode(201)
            .extract()
            .path("id")).longValue();

        given()
            .when()
            .delete("/api/sessions/" + id)
            .then()
            .statusCode(204);

        given()
            .when()
            .get("/api/sessions/" + id)
            .then()
            .statusCode(404);
    }

    @Test
    void testAddAndRemoveSpeakerToSession() {
        // Create a speaker
        Map<String, Object> speaker = new HashMap<>();
        speaker.put("name", "Alice");
        Long speakerId = ((Number) given()
            .contentType(ContentType.JSON)
            .body(speaker)
            .when()
            .post("/api/speakers")
            .then()
            .statusCode(201)
            .extract()
            .path("id")).longValue();

        // Create a session
        Map<String, Object> session = new HashMap<>();
        session.put("title", "Panel");
        Long sessionId = ((Number) given()
            .contentType(ContentType.JSON)
            .body(session)
            .when()
            .post("/api/sessions")
            .then()
            .statusCode(201)
            .extract()
            .path("id")).longValue();

        // Add speaker to session
        given()
            .when()
            .post("/api/sessions/" + sessionId + "/speakers/" + speakerId)
            .then()
            .statusCode(200)
            .body("speakers", hasSize(1))
            .body("speakers[0].id", is(speakerId.intValue()));

        // Remove speaker from session
        given()
            .when()
            .delete("/api/sessions/" + sessionId + "/speakers/" + speakerId)
            .then()
            .statusCode(204);

        // Verify speaker removed
        given()
            .when()
            .get("/api/sessions/" + sessionId)
            .then()
            .statusCode(200)
            .body("speakers", hasSize(0));
    }

    @Test
    void testAddSpeakerToNonExistentSession() {
        Map<String, Object> speaker = new HashMap<>();
        speaker.put("name", "Bob");
        Long speakerId = ((Number) given()
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
            .post("/api/sessions/9999/speakers/" + speakerId)
            .then()
            .statusCode(404);
    }

    @Test
    void testRemoveSpeakerFromNonExistentSession() {
        given()
            .when()
            .delete("/api/sessions/9999/speakers/1")
            .then()
            .statusCode(404);
    }
} 