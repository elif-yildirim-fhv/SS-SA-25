package at.fhv.sys.hotel.query.controller;

import at.fhv.sys.hotel.commands.shared.events.PaymentReceived;
import at.fhv.sys.hotel.projection.PaymentProjection;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logmanager.Logger;

@Path("/api/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentQueryController {

    @Inject
    PaymentProjection paymentProjection;

    public PaymentQueryController() {
    }

    @POST
    @Path("/paymentCreated")
    public Response paymentCreated(PaymentReceived event) {
        Logger.getAnonymousLogger().info("Received event: " + event);
        paymentProjection.processPaymentCreatedEvent(event);
        return Response.ok(event).build();
    }

    @GET
    @Path("/{paymentId}")
    public Response getPaymentById(@PathParam("paymentId") String paymentId) {
        return Response.ok(paymentProjection.getPaymentById(paymentId)).build();
    }

    @GET
    @Path("/booking/{bookingId}")
    public Response getPaymentsByBookingId(@PathParam("bookingId") String bookingId) {
        return Response.ok(paymentProjection.getPaymentsByBookingId(bookingId)).build();
    }

    @GET
    public Response getAllPayments() {
        return Response.ok(paymentProjection.getAllPayments()).build();
    }

    @GET
    @Path("/method/{paymentMethod}")
    public Response getPaymentsByMethod(@PathParam("paymentMethod") String paymentMethod) {
        return Response.ok(paymentProjection.getPaymentsByMethod(paymentMethod)).build();
    }
}
