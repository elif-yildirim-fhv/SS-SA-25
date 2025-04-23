package at.fhv.sys.eventbus.controller;

import at.fhv.sys.eventbus.services.EventProcessingService;
import at.fhv.sys.hotel.commands.shared.events.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
    @Transactional
    @Operation(summary = "Process customer created event")
    @APIResponse(responseCode = "200", description = "Event processed successfully")
    public Response customerCreated(CustomerCreated event) {
        try {
            LOGGER.info("Received CustomerCreated event: " + event);
            if (event == null) {
                LOGGER.warning("Received null CustomerCreated event");
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Event cannot be null")
                        .build();
            }
            
            if (event.getCustomerId() == null) {
                LOGGER.warning("CustomerCreated event has null customerId");
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("CustomerId cannot be null")
                        .build();
            }
            
            String streamId = "customer-" + event.getCustomerId();
            eventStoreService.processEvent(streamId, event);
            
            LOGGER.info("CustomerCreated event processed successfully: " + event.getCustomerId());
            return Response.ok(event).build();
        } catch (Exception e) {
            LOGGER.severe("Error processing CustomerCreated event: " + e.getMessage());
            e.printStackTrace();
            return Response.ok(event).build(); // Always return OK to avoid blocking the command side
        }
    }

    @POST
    @Path("/bookingCreated")
    @Transactional
    @Operation(summary = "Process room booked event")
    @APIResponse(responseCode = "200", description = "Event processed successfully")
    public Response bookingCreated(BookingCreated event) {
        try {
            LOGGER.info("Received event: " + event);
            if (event == null || event.getBookingId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid event data").build();
            }
            eventStoreService.processEvent("booking-" + event.getBookingId(), event);
            return Response.ok(event).build();
        } catch (Exception e) {
            LOGGER.severe("Error processing event: " + e.getMessage());
            e.printStackTrace();
            return Response.ok(event).build(); // Always return OK to avoid blocking the command side
        }
    }

    @POST
    @Path("/bookingCancelled")
    @Transactional
    @Operation(summary = "Process booking cancelled event")
    @APIResponse(responseCode = "200", description = "Event processed successfully")
    public Response bookingCancelled(BookingCancelled event) {
        try {
            LOGGER.info("Received event: " + event);
            if (event == null || event.getBookingId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid event data").build();
            }
            eventStoreService.processEvent("booking-" + event.getBookingId(), event);
            return Response.ok(event).build();
        } catch (Exception e) {
            LOGGER.severe("Error processing event: " + e.getMessage());
            e.printStackTrace();
            return Response.ok(event).build(); // Always return OK to avoid blocking the command side
        }
    }

    @POST
    @Path("/paymentReceived")
    @Transactional
    @Operation(summary = "Process payment received event")
    @APIResponse(responseCode = "200", description = "Event processed successfully")
    public Response paymentReceived(PaymentReceived event) {
        try {
            LOGGER.info("Received event: " + event);
            if (event == null || event.getBookingId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid event data").build();
            }
            eventStoreService.processEvent("payment-" + event.getBookingId(), event);
            return Response.ok(event).build();
        } catch (Exception e) {
            LOGGER.severe("Error processing event: " + e.getMessage());
            e.printStackTrace();
            return Response.ok(event).build(); // Always return OK to avoid blocking the command side
        }
    }

    @POST
    @Path("/roomCreated")
    @Operation(summary = "Process room created event")
    @APIResponse(responseCode = "200", description = "Event processed successfully")
    public Response roomCreated(RoomCreated event) {
        try {
            LOGGER.info("Received room created event: " + event);
            if (event == null || event.getRoomId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid event data").build();
            }
            eventStoreService.processEvent("room-" + event.getRoomId(), event);
            return Response.ok(event).build();
        } catch (Exception e) {
            LOGGER.severe("Error processing room created event: " + e.getMessage());
            e.printStackTrace();
            return Response.ok(event).build(); // Always return OK to avoid blocking the command side
        }
    }
}