package at.fhv.sys.eventbus.client;

import at.fhv.sys.hotel.commands.shared.events.*;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey="hotel-query-api-client")
@Path("/api")
public interface QueryClient {

    @POST
    @Path("/customerCreated")
    @Consumes(MediaType.APPLICATION_JSON)
    void forwardCustomerCreatedEvent(CustomerCreated event);

    @POST
    @Path("/customerUpdated")
    @Consumes(MediaType.APPLICATION_JSON)
    void forwardCustomerUpdatedEvent(CustomerUpdated event);

    @POST
    @Path("/customerDeleted")
    @Consumes(MediaType.APPLICATION_JSON)
    void forwardCustomerDeletedEvent(CustomerDeleted event);

    @POST
    @Path("/roomBooked")
    @Consumes(MediaType.APPLICATION_JSON)
    void forwardRoomBookedEvent(BookingCreated event);

    @POST
    @Path("/bookingCancelled")
    @Consumes(MediaType.APPLICATION_JSON)
    void forwardBookingCancelledEvent(BookingCancelled event);
}
