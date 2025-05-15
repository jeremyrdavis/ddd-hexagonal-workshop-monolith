package io.arrogantprogrammer.social;

import io.arrogantprogrammer.events.AttendeeRegistered;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@QuarkusTest
public class SocialMediaResourceTest {
    
    @InjectMock
    SocialMediaService socialMediaService;
    
    @Test
    public void testGetRecentPosts() {
        // Given
        SocialPost post1 = new SocialPost(
            "Welcome John Doe!",
            "Conference Bot",
            "Twitter",
            1L
        );
        SocialPost post2 = new SocialPost(
            "Welcome Jane Smith!",
            "Conference Bot",
            "Twitter",
            2L
        );
        Mockito.when(socialMediaService.getRecentPosts())
            .thenReturn(Arrays.asList(post1, post2));
        
        // When/Then
        given()
            .when().get("/api/social")
            .then()
            .statusCode(200)
            .body("size()", is(2))
            .body("[0].content", is("Welcome John Doe!"))
            .body("[1].content", is("Welcome Jane Smith!"));
    }
    
    @Test
    public void testGetPostsByAttendee() {
        // Given
        SocialPost post = new SocialPost(
            "Welcome John Doe!",
            "Conference Bot",
            "Twitter",
            1L
        );
        Mockito.when(socialMediaService.getPostsByAttendee(1L))
            .thenReturn(Collections.singletonList(post));
        
        // When/Then
        given()
            .when().get("/api/social/attendee/1")
            .then()
            .statusCode(200)
            .body("size()", is(1))
            .body("[0].content", is("Welcome John Doe!"))
            .body("[0].author", is("Conference Bot"))
            .body("[0].platform", is("Twitter"));
    }
    
    @Test
    public void testGetPostsByAttendeeNotFound() {
        // Given
        Mockito.when(socialMediaService.getPostsByAttendee(anyLong()))
            .thenReturn(Collections.emptyList());
        
        // When/Then
        given()
            .when().get("/api/social/attendee/999")
            .then()
            .statusCode(404);
    }
    
    @Test
    public void testGetPost() {
        // Given
        SocialPost post = new SocialPost(
            "Welcome John Doe!",
            "Conference Bot",
            "Twitter",
            1L
        );
        Mockito.when(socialMediaService.getPost(1L))
            .thenReturn(post);
        
        // When/Then
        given()
            .when().get("/api/social/1")
            .then()
            .statusCode(200)
            .body("content", is("Welcome John Doe!"))
            .body("author", is("Conference Bot"))
            .body("platform", is("Twitter"));
    }
    
    @Test
    public void testGetPostNotFound() {
        // Given
        Mockito.when(socialMediaService.getPost(anyLong()))
            .thenReturn(null);
        
        // When/Then
        given()
            .when().get("/api/social/999")
            .then()
            .statusCode(404);
    }
    
    @Test
    public void testDeletePost() {
        // Given
        SocialPost post = new SocialPost(
            "Welcome John Doe!",
            "Conference Bot",
            "Twitter",
            1L
        );
        Mockito.when(socialMediaService.getPost(1L))
            .thenReturn(post);
        
        // When/Then
        given()
            .when().delete("/api/social/1")
            .then()
            .statusCode(204);
        
        Mockito.verify(socialMediaService).deletePost(1L);
    }
    
    @Test
    public void testDeletePostNotFound() {
        // Given
        Mockito.when(socialMediaService.getPost(anyLong()))
            .thenReturn(null);
        
        // When/Then
        given()
            .when().delete("/api/social/999")
            .then()
            .statusCode(404);
    }
} 