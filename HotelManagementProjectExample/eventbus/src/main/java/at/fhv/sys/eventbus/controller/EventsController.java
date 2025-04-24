package at.fhv.sys.eventbus.controller;

import at.fhv.sys.eventbus.client.QueryClient;
import at.fhv.sys.eventbus.services.EventProcessingService;
import at.fhv.sys.hotel.commands.shared.events.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logmanager.Logger;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventsController {
    @Inject
    EventProcessingService eventStoreService;

    @Inject
    @RestClient
    QueryClient queryClient;

    private static final Logger LOGGER = Logger.getLogger(EventsController.class.getName());
    @Inject
    EventProcessingService eventProcessingService;

    @POST
    @Path("/customerCreated")
    @Transactional
    @Operation(summary = "Process customer created event")
    public Response customerCreated(CustomerCreated event) {
        try{
            LOGGER.info("Received CustomerCreated event: " + event);
            eventProcessingService.processEvent("customer-" + event.getCustomerId(), event);
            eventProcessingService.forwardEventToQuerySide(event);
        }catch(JsonProcessingException e){
            LOGGER.warning("Event Processing failed: " + e.getMessage());
        }
        return Response.ok(event).build();
    }

    @POST
    @Path("/bookingCreated")
    @Transactional
    @Operation(summary = "Process booking created event")
    public Response bookingCreated(BookingCreated event) {
        try{
            LOGGER.info("Received BookingCreated event: " + event);
            eventProcessingService.processEvent("booking-" + event.getBookingId(), event);
            eventProcessingService.forwardEventToQuerySide(event);
        }catch (Exception e){
            LOGGER.warning("Event Processing failed: " + e.getMessage());
        }
        return Response.ok(event).build();
    }

    @POST
    @Path("/bookingCancelled")
    @Transactional
    @Operation(summary = "Process booking cancelled event")
    public Response bookingCancelled(BookingCancelled event) {
        try{
            LOGGER.info("Received BookingCancelled event: " + event);
            eventProcessingService.processEvent("booking-" + event.getBookingId(), event);
            eventProcessingService.forwardEventToQuerySide(event);
        }catch (Exception e){
            LOGGER.warning("Event Processing failed: " + e.getMessage());
        }
        return Response.ok(event).build();
    }

    @POST
    @Path("/paymentReceived")
    @Transactional
    @Operation(summary = "Process payment received event")
    public Response paymentReceived(PaymentReceived event) {
        try{
            LOGGER.info("Received PaymentReceived event: " + event);
            eventProcessingService.processEvent("payment-" + event.getPaymentId(), event);
            eventProcessingService.forwardEventToQuerySide(event);
        }catch (Exception e){
            LOGGER.warning("Event Processing failed: " + e.getMessage());
        }
        return Response.ok(event).build();
    }

    @POST
    @Path("/roomCreated")
    @Transactional
    @Operation(summary = "Process room created event")
    public Response roomCreated(RoomCreated event) {
        try{
            LOGGER.info("Received RoomCreated event: " + event);
            eventProcessingService.processEvent("room-" + event.getRoomId(), event);
            eventProcessingService.forwardEventToQuerySide(event);
        }catch (Exception e){
            LOGGER.warning("Event Processing failed: " + e.getMessage());
        }
        return Response.ok(event).build();
    }

}

//        try {
//            LOGGER.info("Received CustomerCreated event: " + event);
//            if (event == null) {
//                LOGGER.warning("Received null CustomerCreated event");
//                return Response.status(Response.Status.BAD_REQUEST)
//                        .entity("Event cannot be null")
//                        .build();
//            }
//
//            if (event.getCustomerId() == null) {
//                LOGGER.warning("CustomerCreated event has null customerId");
//                return Response.status(Response.Status.BAD_REQUEST)
//                        .entity("CustomerId cannot be null")
//                        .build();
//            }
//
//            String streamId = "customer-" + event.getCustomerId();
//            eventStoreService.processEvent(streamId, event);
//
//            LOGGER.info("CustomerCreated event processed successfully: " + event.getCustomerId());
//            return Response.ok(event).build();
//        } catch (Exception e) {
//            LOGGER.severe("Error processing CustomerCreated event: " + e.getMessage());
//            e.printStackTrace();
//            return Response.ok(event).build(); // Always return OK to avoid blocking the command side
//        }
//    }
//