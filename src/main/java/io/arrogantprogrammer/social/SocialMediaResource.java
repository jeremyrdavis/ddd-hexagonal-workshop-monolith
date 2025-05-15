package io.arrogantprogrammer.social;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class SocialMediaResource {
    
    @Inject
    SocialMediaService socialMediaService;
    
    @GET
    public List<SocialPost> getRecentPosts() {
        return socialMediaService.getRecentPosts();
    }
    
    @GET
    @Path("/attendee/{id}")
    public Response getPostsByAttendee(@PathParam("id") Long attendeeId) {
        List<SocialPost> posts = socialMediaService.getPostsByAttendee(attendeeId);
        if (posts.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(posts).build();
    }
    
    @GET
    @Path("/{id}")
    public Response getPost(@PathParam("id") Long id) {
        SocialPost post = socialMediaService.getPost(id);
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(post).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deletePost(@PathParam("id") Long id) {
        SocialPost post = socialMediaService.getPost(id);
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        socialMediaService.deletePost(id);
        return Response.noContent().build();
    }
} 