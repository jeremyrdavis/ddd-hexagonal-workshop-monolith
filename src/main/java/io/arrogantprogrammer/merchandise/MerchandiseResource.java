package io.arrogantprogrammer.merchandise;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/merchandise")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MerchandiseResource {

    @Inject
    MerchandiseService merchandiseService;

    @GET
    @Path("/orders")
    public List<MerchandiseOrder> getAllOrders() {
        return merchandiseService.getAllOrders();
    }

    @GET
    @Path("/orders/{attendeeId}")
    public Response getOrdersByAttendee(@PathParam("attendeeId") Long attendeeId) {
        List<MerchandiseOrder> orders = merchandiseService.getOrdersByAttendee(attendeeId);
        if (orders.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(orders).build();
    }

    @PUT
    @Path("/orders/{orderId}/status")
    public Response updateOrderStatus(@PathParam("orderId") Long orderId, @QueryParam("status") String status) {
        merchandiseService.updateOrderStatus(orderId, status);
        return Response.ok().build();
    }
} 