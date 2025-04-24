package at.fhv.sys.hotel.query.controller;

import at.fhv.sys.hotel.commands.shared.events.PaymentReceived;
import at.fhv.sys.hotel.projection.PaymentProjection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logmanager.Logger;

@Path("/api/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PaymentQueryController {

    @Inject
    PaymentProjection paymentProjection;

    public PaymentQueryController() {
    }

    @POST
    @Path("/paymentCreated")
    public Response paymentCreated(PaymentReceived event) {
        Logger.getAnonymousLogger().info("Received event: " + event);
        paymentProjection.processPaymentReceivedEvent(event);
        return Response.ok(event).build();
    }


}
