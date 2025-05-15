package io.arrogantprogrammer.catering;

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
import static org.mockito.Mockito.when;

@QuarkusTest
class CateringResourceTest {

    @InjectMock
    CateringService cateringService;

    @Test
    void testGetAllDietaryRequirements() {
        // Setup
        List<DietaryRequirement> requirements = Arrays.asList(
            createDietaryRequirement(1L, "Vegetarian", "No nuts"),
            createDietaryRequirement(2L, "Vegan", null)
        );
        when(cateringService.getAllDietaryRequirements()).thenReturn(requirements);

        // Test
        given()
            .when().get("/api/catering/dietary")
            .then()
            .statusCode(200)
            .body("size()", is(2))
            .body("[0].attendeeId", is(1))
            .body("[0].preference", is("Vegetarian"))
            .body("[0].specialRequests", is("No nuts"))
            .body("[1].attendeeId", is(2))
            .body("[1].preference", is("Vegan"));
    }

    @Test
    void testGetDietaryRequirementsByAttendee() {
        // Setup
        List<DietaryRequirement> requirements = Collections.singletonList(
            createDietaryRequirement(1L, "Vegetarian", "No nuts")
        );
        when(cateringService.getDietaryRequirementsByAttendee(1L)).thenReturn(requirements);

        // Test
        given()
            .when().get("/api/catering/dietary/1")
            .then()
            .statusCode(200)
            .body("size()", is(1))
            .body("[0].attendeeId", is(1))
            .body("[0].preference", is("Vegetarian"))
            .body("[0].specialRequests", is("No nuts"));
    }

    @Test
    void testGetDietaryRequirementsByAttendeeNotFound() {
        // Setup
        when(cateringService.getDietaryRequirementsByAttendee(999L)).thenReturn(Collections.emptyList());

        // Test
        given()
            .when().get("/api/catering/dietary/999")
            .then()
            .statusCode(404);
    }

    private DietaryRequirement createDietaryRequirement(Long attendeeId, String preference, String specialRequests) {
        DietaryRequirement requirement = new DietaryRequirement();
        requirement.setAttendeeId(attendeeId);
        requirement.setPreference(preference);
        requirement.setSpecialRequests(specialRequests);
        return requirement;
    }
} 