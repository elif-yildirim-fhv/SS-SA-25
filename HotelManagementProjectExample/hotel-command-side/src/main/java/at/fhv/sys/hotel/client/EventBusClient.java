package at.fhv.sys.hotel.client;

import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.commands.shared.events.CustomerDeleted;
import at.fhv.sys.hotel.commands.shared.events.CustomerUpdated;
import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.Path;

@RegisterRestClient(configKey="hotel-eventbus-api-client")
@Path("/api")
public interface EventBusClient {

    @POST
    @Path("/customerCreated")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    CustomerCreated processCustomerCreatedEvent(CustomerCreated event);

    @POST
    @Path("/customerUpdated")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    CustomerUpdated processCustomerUpdatedEvent(CustomerUpdated event);

    @POST
    @Path("/customerDeleted")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    CustomerDeleted processCustomerDeletedEvent(CustomerDeleted event);

    @POST
    @Path("/bookingCreated")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    BookingCreated processBookingCreatedEvent(BookingCreated event);
}
