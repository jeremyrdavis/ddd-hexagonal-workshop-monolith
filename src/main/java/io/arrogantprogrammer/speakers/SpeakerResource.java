package io.arrogantprogrammer.speakers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import jakarta.validation.Valid;

import java.util.Optional;

@Path("/api/speakers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpeakerResource {

    @Inject
    SpeakerService speakerService;

    @GET
    public List<SpeakerDTO> getAllSpeakers() {
        return speakerService.getAllSpeakers().stream()
                .map(SpeakerDTO::new)
                .toList();
    }

    @GET
    @Path("/{id}")
    public Response getSpeaker(@PathParam("id") Long id) {
        return speakerService.getSpeaker(id)
                .map(speaker -> Response.ok(new SpeakerDTO(speaker)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    public Response createSpeaker(@Valid Speaker speaker) {
        Speaker created = speakerService.createSpeaker(speaker);
        return Response.status(Response.Status.CREATED)
                .entity(new SpeakerDTO(created))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateSpeaker(@PathParam("id") Long id, Speaker speaker) {
        return speakerService.updateSpeaker(id, speaker)
                .map(updated -> Response.ok(new SpeakerDTO(updated)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteSpeaker(@PathParam("id") Long id) {
        if (speakerService.deleteSpeaker(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/{id}/social")
    @Transactional
    public Response addSocialMedia(@PathParam("id") Long id, @Valid SocialMedia socialMedia) {
        return speakerService.addSocialMedia(id, socialMedia)
                .map(updated -> {
                    // Find the just-added social media (by handle and platform)
                    SocialMedia created = updated.socialMedia.stream()
                        .filter(sm -> sm.platform.equals(socialMedia.platform) && sm.handle.equals(socialMedia.handle))
                        .findFirst()
                        .orElse(null);
                    if (created != null) {
                        return Response.status(Response.Status.CREATED)
                                .entity(created)
                                .build();
                    } else {
                        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}/social/{socialMediaId}")
    @Transactional
    public Response removeSocialMedia(@PathParam("id") Long id, @PathParam("socialMediaId") Long socialMediaId) {
        Optional<Speaker> speaker = speakerService.removeSocialMedia(id, socialMediaId);
        if (speaker.isPresent()) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
} 