package at.fhv.sys.eventbus.client;

import at.fhv.sys.hotel.commands.shared.events.*;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.logmanager.Logger;

@RegisterRestClient(configKey="hotel-query-api-client")
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public interface QueryClient {
    
    Logger LOGGER = Logger.getLogger(QueryClient.class.getName());

    @POST
    @Path("/customerCreated")
    @Consumes(MediaType.APPLICATION_JSON)
    void forwardCustomerCreatedEvent(CustomerCreated event);
    
    @POST
    @Path("/customerUpdated")
    @Consumes(MediaType.APPLICATION_JSON)
    void forwardCustomerUpdatedEvent(CustomerUpdated event);

    @POST
    @Path("/bookingCreated")
    @Consumes(MediaType.APPLICATION_JSON)
    void forwardRoomBookedEvent(BookingCreated event);

    @POST
    @Path("/bookingCancelled")
    @Consumes(MediaType.APPLICATION_JSON)
    void forwardBookingCancelledEvent(BookingCancelled event);

    @POST
    @Path("/paymentCreated")
    @Consumes(MediaType.APPLICATION_JSON)
    PaymentReceived processPaymentCreatedEvent(PaymentReceived event);

    @POST
    @Path("/roomCreated")
    @Consumes(MediaType.APPLICATION_JSON)
    void processRoomCreatedEvent(RoomCreated event);
}


