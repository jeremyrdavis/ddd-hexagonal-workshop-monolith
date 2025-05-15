package io.arrogantprogrammer.catering;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/catering")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CateringResource {

    @Inject
    CateringService cateringService;

    @GET
    @Path("/dietary")
    public List<DietaryRequirement> getAllDietaryRequirements() {
        return cateringService.getAllDietaryRequirements();
    }

    @GET
    @Path("/dietary/{attendeeId}")
    public Response getDietaryRequirementsByAttendee(@PathParam("attendeeId") Long attendeeId) {
        List<DietaryRequirement> requirements = cateringService.getDietaryRequirementsByAttendee(attendeeId);
        if (requirements.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(requirements).build();
    }
} 