package io.arrogantprogrammer.cfp;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST resource for speakers.
 */
@Path("/api/cfp/speakers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpeakerResource {
    
    @Inject
    SpeakerService speakerService;
    
    @Inject
    SpeakerMapper speakerMapper;
    
    /**
     * Gets all speakers.
     * 
     * @return a list of all speakers
     */
    @GET
    public List<SpeakerDTO> getAllSpeakers() {
        return speakerService.getAllSpeakers().stream()
                .map(speakerMapper::toDTO)
                .collect(Collectors.toList());
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
        return speakerService.getSpeaker(id)
                .map(speakerMapper::toDTO)
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
        Speaker speaker = speakerMapper.toEntity(dto);
        Speaker created = speakerService.createSpeaker(speaker);
        return Response.status(Response.Status.CREATED)
                .entity(speakerMapper.toDTO(created))
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
        return speakerService.getSpeaker(id)
                .map(speaker -> {
                    Speaker updated = speakerMapper.updateEntityFromDTO(speaker, dto);
                    return Response.ok(speakerMapper.toDTO(updated)).build();
                })
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
        if (speakerService.deleteSpeaker(id)) {
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
    public List<SpeakerDTO> searchSpeakers(@QueryParam("name") String query) {
        return speakerService.searchByName(query).stream()
                .map(speakerMapper::toDTO)
                .collect(Collectors.toList());
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
        return speakerService.findByCompany(company).stream()
                .map(speakerMapper::toDTO)
                .collect(Collectors.toList());
    }
}