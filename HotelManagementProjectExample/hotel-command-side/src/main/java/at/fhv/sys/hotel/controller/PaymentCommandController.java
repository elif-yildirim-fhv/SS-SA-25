package at.fhv.sys.hotel.controller;

import at.fhv.sys.hotel.commands.*;
import at.fhv.sys.hotel.domain.Payment;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Payment Commands", description = "Endpoints for payment-related commands")
public class PaymentCommandController {

    @Inject
    BookingAggregate bookingAggregate;

    @POST
    @Path("/processPayment")
    @Operation(summary = "Process payment for a booking")
    @APIResponse(responseCode = "200", description = "Payment processed successfully")
    @APIResponse(responseCode = "400", description = "Invalid payment request")
    @APIResponse(responseCode = "404", description = "Booking not found")
    public Response processPayment(
            @QueryParam("bookingId") String bookingId,

            @Parameter(
                    description = "Insert: CREDIT_CARD, CASH, BANK_TRANSFER, PAYPAL"
            )
            @QueryParam("paymentMethod") String paymentMethod
    ) {
        try {
            String paymentId = bookingAggregate.handle(
                    new PayBookingCommand(bookingId, paymentMethod)
            );
            return Response.ok("Payment processed successfully: " + paymentId).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error processing payment: " + e.getMessage())
                    .build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Booking not found")
                    .build();
        }
    }

}