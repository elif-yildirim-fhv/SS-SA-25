package at.fhv.sys.eventbus.controller;

import at.fhv.sys.eventbus.services.EventProcessingService;
import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.commands.shared.events.BookingCancelled;
import at.fhv.sys.hotel.commands.shared.events.PaymentReceived;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logmanager.Logger;


@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventsController {
    @Inject
    EventProcessingService eventStoreService;

    public EventsController() {
    }

    @POST
    @Path("/customerCreated")
    @Operation(summary = "Process customer created event")
    @APIResponse(responseCode = "200", description = "Event processed successfully")
    public Response customerCreated(CustomerCreated event) {
        Logger.getAnonymousLogger().info("Received event: " + event);
        eventStoreService.processEvent("customer-" + event.getCustomerId(), event);
        return Response.ok(event).build();
    }

    @POST
    @Path("/bookingCreated")
    @Operation(summary = "Process room booked event")
    @APIResponse(responseCode = "200", description = "Event processed successfully")
    public Response bookingCreated(BookingCreated event) {
        Logger.getAnonymousLogger().info("Received event: " + event);
        eventStoreService.processEvent("room-" + event.getBookingId(), event);
        return Response.ok(event).build();
    }

    @POST
    @Path("/bookingCancelled")
    @Operation(summary = "Process booking cancelled event")
    @APIResponse(responseCode = "200", description = "Event processed successfully")
    public Response bookingCancelled(BookingCancelled event) {
        Logger.getAnonymousLogger().info("Received event: " + event);
        eventStoreService.processEvent("booking-" + event.getBookingId(), event);
        return Response.ok(event).build();
    }

    @POST
    @Path("/paymentCreated")
    @Operation(summary = "Process payment received event")
    @APIResponse(responseCode = "200", description = "Event processed successfully")
    public Response paymentCreated(PaymentReceived event) {
        Logger.getAnonymousLogger().info("Received event: " + event);
        eventStoreService.processEvent("payment-" + event.getBookingId(), event);
        return Response.ok(event).build();
    }
}