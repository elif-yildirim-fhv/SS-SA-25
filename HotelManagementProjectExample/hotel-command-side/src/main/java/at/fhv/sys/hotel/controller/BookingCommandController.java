package at.fhv.sys.hotel.controller;

import at.fhv.sys.hotel.commands.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Booking Commands", description = "Endpoints for booking-related commands")
public class BookingCommandController {

    @Inject
    BookingAggregate bookingAggregate;

    @POST
    @Path("/createBooking")
    @Operation(summary = "Create a new booking")
    @APIResponse(responseCode = "200", description = "Booking created successfully")
    @APIResponse(responseCode = "400", description = "Invalid booking data")
    public Response createBooking(@QueryParam("roomId") String roomId,
                                @QueryParam("customerId") String customerId,
                                @QueryParam("startDate") String startDate,
                                @QueryParam("endDate") String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            
            String bookingId = bookingAggregate.handle(
                new BookRoomCommand(
                    null,
                    roomId,
                    customerId,
                    start,
                    end,
                    0.0,
                    false,
                    false
                )
            );
            
            return Response.ok("Booking created with ID: " + bookingId).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid date format. Please use YYYY-MM-DD")
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error creating booking: " + e.getMessage())
                    .build();
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Error creating booking: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/{bookingId}/cancel")
    @Operation(summary = "Cancel a booking")
    @APIResponse(responseCode = "200", description = "Booking cancelled successfully")
    @APIResponse(responseCode = "404", description = "Booking not found")
    public Response cancelBooking(@PathParam("bookingId") String bookingId) {
        try {
            bookingAggregate.handle(new CancelBookingCommand(bookingId));
            return Response.ok("Booking cancelled successfully").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Booking not found: " + e.getMessage())
                    .build();
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Cannot cancel booking: " + e.getMessage())
                    .build();
        }
    }
} 