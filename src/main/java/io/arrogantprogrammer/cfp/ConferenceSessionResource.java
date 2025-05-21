package io.arrogantprogrammer.cfp;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST resource for conference sessions.
 */
@Path("/api/cfp/sessions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConferenceSessionResource {

    @Inject
    CFPService cfpService;

    /**
     * Gets all conference sessions.
     * 
     * @return a list of all conference sessions
     */
    @GET
    public List<ConferenceSessionDTO> getAllSessions() {
        return cfpService.getAllConferenceSessions();
    }

    /**
     * Gets a conference session by ID.
     * 
     * @param id the session ID
     * @return the session if found, or a 404 response
     */
    @GET
    @Path("/{id}")
    public Response getSession(@PathParam("id") Long id) {
        return cfpService.getSession(id)
                .map(dto -> Response.ok(dto).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Creates a new conference session.
     * 
     * @param dto the session data
     * @return a 201 response with the created session
     */
    @POST
    @Transactional
    public Response createSession(@Valid ConferenceSessionDTO dto) {
        ConferenceSessionDTO created = cfpService.createSession(dto);
        Log.debugf("Created session: %s", created);
        return Response.status(Response.Status.CREATED)
                .entity(created)
                .build();
    }

    /**
     * Updates an existing conference session.
     * 
     * @param id the ID of the session to update
     * @param dto the updated session data
     * @return the updated session if found, or a 404 response
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateSession(@PathParam("id") Long id, @Valid ConferenceSessionDTO dto) {
        return cfpService.updateSession(id, dto)
                .map(updated -> Response.ok(updated).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Deletes a conference session.
     * 
     * @param id the ID of the session to delete
     * @return a 204 response if deleted, or a 404 response
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteSession(@PathParam("id") Long id) {
        if (cfpService.deleteSession(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Adds a speaker to a session.
     * 
     * @param id the ID of the session
     * @param speakerId the ID of the speaker
     * @return the updated session if found, or a 404 response
     */
    @POST
    @Path("/{id}/speakers/{speakerId}")
    @Transactional
    public Response addSpeakerToSession(@PathParam("id") Long id, @PathParam("speakerId") Long speakerId) {
        return cfpService.addSpeakerToSession(id, speakerId)
                .map(updated -> Response.ok(updated).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Removes a speaker from a session.
     * 
     * @param id the ID of the session
     * @param speakerId the ID of the speaker
     * @return a 204 response if removed, or a 404 response
     */
    @DELETE
    @Path("/{id}/speakers/{speakerId}")
    @Transactional
    public Response removeSpeakerFromSession(@PathParam("id") Long id, @PathParam("speakerId") Long speakerId) {
        return cfpService.removeSpeakerFromSession(id, speakerId)
                .map(session -> Response.noContent().build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Accepts a session.
     * 
     * @param id the ID of the session to accept
     * @return the updated session if found, or a 404 response
     */
    @POST
    @Path("/{id}/accept")
    @Transactional
    public Response acceptSession(@PathParam("id") Long id) {
        return cfpService.acceptSession(id)
                .map(updated -> Response.ok(updated).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Rejects a session.
     * 
     * @param id the ID of the session to reject
     * @return the updated session if found, or a 404 response
     */
    @POST
    @Path("/{id}/reject")
    @Transactional
    public Response rejectSession(@PathParam("id") Long id) {
        return cfpService.rejectSession(id)
                .map(updated -> Response.ok(updated).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Withdraws a session.
     * 
     * @param id the ID of the session to withdraw
     * @return the updated session if found, or a 404 response
     */
    @POST
    @Path("/{id}/withdraw")
    @Transactional
    public Response withdrawSession(@PathParam("id") Long id) {
        return cfpService.withdrawSession(id)
                .map(updated -> Response.ok(updated).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Gets sessions by status.
     * 
     * @param status the session status
     * @return a list of sessions with the given status
     */
    @GET
    @Path("/status/{status}")
    public List<ConferenceSessionDTO> getSessionsByStatus(@PathParam("status") String status) {
        ConferenceSession.SessionStatus sessionStatus = ConferenceSession.SessionStatus.valueOf(status.toUpperCase());
        return cfpService.findSessionsByStatus(sessionStatus);
    }

    /**
     * Gets sessions by speaker.
     * 
     * @param speakerId the ID of the speaker
     * @return a list of sessions for the speaker
     */
    @GET
    @Path("/speaker/{speakerId}")
    public List<ConferenceSessionDTO> getSessionsBySpeaker(@PathParam("speakerId") Long speakerId) {
        return cfpService.findSessionsBySpeaker(speakerId);
    }
}
