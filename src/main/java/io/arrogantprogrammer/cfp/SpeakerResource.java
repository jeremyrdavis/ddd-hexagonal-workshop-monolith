package io.arrogantprogrammer.cfp;

import io.arrogantprogrammer.cfp.domain.services.CFPService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST resource for speakers.
 */
@Path("/api/cfp/speakers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpeakerResource {

    @Inject
    CFPService cfpService;

    /**
     * Gets all speakers.
     * 
     * @return a list of all speakers
     */
    @GET
    public List<SpeakerDTO> getAllSpeakers() {
        return cfpService.getAllSpeakers();
    }

    /**
     * Gets a speaker by ID.
     * 
     * @param id the speaker ID
     * @return the speaker if found, or a 404 response
     */
    @GET
    @Path("/{id}")
    public Response getSpeaker(@PathParam("id") Long id) {
        return cfpService.getSpeaker(id)
                .map(dto -> Response.ok(dto).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Creates a new speaker.
     * 
     * @param dto the speaker data
     * @return a 201 response with the created speaker
     */
    @POST
    @Transactional
    public Response createSpeaker(@Valid SpeakerDTO dto) {
        SpeakerDTO created = cfpService.registerSpeaker(dto);
        return Response.status(Response.Status.CREATED)
                .entity(created)
                .build();
    }

    /**
     * Updates an existing speaker.
     * 
     * @param id the ID of the speaker to update
     * @param dto the updated speaker data
     * @return the updated speaker if found, or a 404 response
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateSpeaker(@PathParam("id") Long id, @Valid SpeakerDTO dto) {
        return cfpService.updateSpeaker(id, dto)
                .map(updatedSpeaker -> Response.ok(updatedSpeaker).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Deletes a speaker.
     * 
     * @param id the ID of the speaker to delete
     * @return a 204 response if deleted, or a 404 response
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteSpeaker(@PathParam("id") Long id) {
        if (cfpService.deleteSpeaker(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Searches for speakers by name.
     * 
     * @param query the name query
     * @return a list of speakers matching the query
     */
    @GET
    @Path("/search")
    public List<SpeakerDTO> searchSpeakers(@QueryParam("query") String query) {
        return cfpService.searchByName(query);
    }

    /**
     * Finds speakers by company.
     * 
     * @param company the company name
     * @return a list of speakers from the given company
     */
    @GET
    @Path("/company/{company}")
    public List<SpeakerDTO> getSpeakersByCompany(@PathParam("company") String company) {
        return cfpService.findByCompany(company);
    }
}
