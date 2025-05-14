package io.arrogantprogrammer.health;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/health")
public class HealthResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public java.util.Map<String, String> health() {
        return java.util.Collections.singletonMap("status", "UP");
    }
}
