package at.fhv.sys.hotel.client;

import at.fhv.sys.hotel.commands.shared.events.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

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
    @Path("/bookingCreated")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    BookingCreated processBookingCreatedEvent(BookingCreated event);

    @POST
    @Path("/bookingCancelled")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    BookingCancelled processBookingCancelledEvent(BookingCancelled event);

    @POST
    @Path("/paymentReceived")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    PaymentReceived processPaymentReceivedEvent(PaymentReceived event);

}
