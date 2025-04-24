package at.fhv.sys.hotel.query.controller.booking;

import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.BookingCancelled;
import at.fhv.sys.hotel.projection.booking.BookingProjection;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logmanager.Logger;
import java.time.LocalDate;
import java.util.List;

@Path("/api/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingQueryController {
    private static final Logger LOGGER = Logger.getLogger(BookingQueryController.class.getName());

    @Inject
    BookingProjection bookingProjection;

    @POST
    @Path("/events/created")
    public Response handleBookingCreated(BookingCreated event) {
        LOGGER.info("Received BookingCreated event: " + event);
        bookingProjection.processCreated(event);
        return Response.ok().build();
    }

    @POST
    @Path("/events/cancelled")
    public Response handleBookingCancelled(BookingCancelled event) {
        LOGGER.info("Received BookingCancelled event: " + event);
        bookingProjection.processCancelled(event);
        return Response.ok().build();
    }

    @GET
    public Response getBookings(
        @QueryParam("startDate") String startDateStr,
        @QueryParam("endDate")   String endDateStr) {

        LocalDate start = LocalDate.parse(startDateStr);
        LocalDate end   = LocalDate.parse(endDateStr);

        List<?> bookings = bookingProjection.getByRange(start, end);
        return Response.ok(bookings).build();
    }
} 