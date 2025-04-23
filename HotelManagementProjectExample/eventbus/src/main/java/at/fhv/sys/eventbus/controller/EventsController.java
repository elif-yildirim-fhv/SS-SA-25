package at.fhv.sys.eventbus.controller;

import at.fhv.sys.eventbus.services.EventProcessingService;
import at.fhv.sys.hotel.commands.shared.events.*;
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

    private static final Logger LOGGER = Logger.getLogger(EventsController.class.getName());

    @POST
    @Path("/customerCreated")
    @Operation(summary = "Process customer created event")
    @APIResponse(responseCode = "200", description = "Event processed successfully")
    public Response customerCreated(CustomerCreated event) {
        LOGGER.info("Received event: " + event);
        eventStoreService.processEvent("customer-" + event.getCustomerId(), event);
        return Response.ok().build();
    }

    @POST
    @Path("/bookingCreated")
    @Operation(summary = "Process room booked event")
    @APIResponse(responseCode = "200", description = "Event processed successfully")
    public Response bookingCreated(BookingCreated event) {
        LOGGER.info("Received event: " + event);
        eventStoreService.processEvent("booking-" + event.getBookingId(), event);
        return Response.ok().build();
    }

    @POST
    @Path("/bookingCancelled")
    @Operation(summary = "Process booking cancelled event")
    @APIResponse(responseCode = "200", description = "Event processed successfully")
    public Response bookingCancelled(BookingCancelled event) {
        LOGGER.info("Received event: " + event);
        eventStoreService.processEvent("booking-" + event.getBookingId(), event);
        return Response.ok().build();
    }

    @POST
    @Path("/paymentReceived")
    @Operation(summary = "Process payment received event")
    @APIResponse(responseCode = "200", description = "Event processed successfully")
    public Response paymentReceived(PaymentReceived event) {
        LOGGER.info("Received event: " + event);
        eventStoreService.processEvent("payment-" + event.getBookingId(), event);
        return Response.ok().build();
    }

    @POST
    @Path("/roomCreated")
    @Operation(summary = "Process room created event")
    @APIResponse(responseCode = "200", description = "Event processed successfully")
    public Response roomCreated(RoomCreated event) {
        try {
            LOGGER.info("Received room created event: " + event);
            eventStoreService.processEvent("room-" + event.getRoomId(), event);
            return Response.ok().build();
        } catch (Exception e) {
            LOGGER.severe("Error processing room created event: " + e.getMessage());
            return Response.serverError().entity("Error processing event: " + e.getMessage()).build();
        }
    }
}