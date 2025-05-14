package io.arrogantprogrammer.locations;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/locations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocationResource {

    @Inject
    LocationService locationService;

    @POST
    @Transactional
    public Response createLocation(Location location) {
        Location created = locationService.createLocation(location);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GET
    @Path("/{id}")
    public Location getLocationById(@PathParam("id") Long id) {
        return locationService.getLocationById(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Location updateLocation(@PathParam("id") Long id, Location location) {
        return locationService.updateLocation(id, location);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteLocation(@PathParam("id") Long id) {
        locationService.deleteLocation(id);
        return Response.noContent().build();
    }
} 