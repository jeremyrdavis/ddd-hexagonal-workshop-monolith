package io.arrogantprogrammer.sessions;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/sessions")
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource {

    @Inject
    SessionService sessionService;
    
    @Inject
    SessionMapper sessionMapper;

    @GET
    public List<SessionDTO> getAllSessions() {
        return sessionService.getAllSessions().stream()
            .map(sessionMapper::toDTO)
            .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response getSession(@PathParam("id") Long id) {
        return sessionService.getSession(id)
                .map(sessionMapper::toDTO)
                .map(session -> Response.ok(session).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSession(@Valid SessionDTO sessionDTO) {
        Session session = sessionMapper.toEntity(sessionDTO);
        Session created = sessionService.createSession(session);
        return Response.status(Response.Status.CREATED)
                .entity(sessionMapper.toDTO(created))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSession(@PathParam("id") Long id, @Valid SessionDTO sessionDTO) {
        Session session = sessionMapper.toEntity(sessionDTO);
        return sessionService.updateSession(id, session)
                .map(sessionMapper::toDTO)
                .map(updated -> Response.ok(updated).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteSession(@PathParam("id") Long id) {
        if (sessionService.deleteSession(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/{id}/speakers/{speakerId}")
    @Transactional
    public Response addSpeakerToSession(@PathParam("id") Long id, @PathParam("speakerId") Long speakerId) {
        return sessionService.addSpeakerToSession(id, speakerId)
                .map(sessionMapper::toDTO)
                .map(updated -> Response.ok(updated).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}/speakers/{speakerId}")
    @Transactional
    public Response removeSpeakerFromSession(@PathParam("id") Long id, @PathParam("speakerId") Long speakerId) {
        return sessionService.removeSpeakerFromSession(id, speakerId)
                .map(sessionMapper::toDTO)
                .map(updated -> Response.noContent().build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
} 