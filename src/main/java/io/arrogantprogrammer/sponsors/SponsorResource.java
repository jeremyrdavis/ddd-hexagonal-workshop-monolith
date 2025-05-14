package io.arrogantprogrammer.sponsors;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/sponsors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SponsorResource {

    @Inject
    SponsorService sponsorService;

    @POST
    @Transactional
    public Response createSponsor(Sponsor sponsor) {
        Sponsor created = sponsorService.createSponsor(sponsor);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    public List<Sponsor> getAllSponsors() {
        return sponsorService.getAllSponsors();
    }

    @GET
    @Path("/{id}")
    public Sponsor getSponsorById(@PathParam("id") Long id) {
        return sponsorService.getSponsorById(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Sponsor updateSponsor(@PathParam("id") Long id, Sponsor sponsor) {
        return sponsorService.updateSponsor(id, sponsor);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteSponsor(@PathParam("id") Long id) {
        sponsorService.deleteSponsor(id);
        return Response.noContent().build();
    }
} 