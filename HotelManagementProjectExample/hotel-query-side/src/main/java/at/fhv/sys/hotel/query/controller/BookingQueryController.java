package at.fhv.sys.hotel.query.controller;

import at.fhv.sys.hotel.commands.shared.events.BookingCancelled;
import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.models.BookingQueryPanacheModel;
import at.fhv.sys.hotel.projection.BookingProjection;
import at.fhv.sys.hotel.service.BookingServicePanache;
import at.fhv.sys.hotel.DTO.GetBookingsDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logmanager.Logger;

import java.time.LocalDate;
import java.util.List;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingQueryController {

	@Inject
	BookingProjection bookingProjection;

	@Inject
	BookingServicePanache bookingService;

	public BookingQueryController() {
	}

	@POST
	@Path("/bookingCreated")
	public Response BookingCreated(BookingCreated event) {
		Logger.getAnonymousLogger().info("Received event: " + event);
		bookingProjection.processBookingCreatedEvent(event);
		return Response.ok(event).build();
	}

	@POST
	@Path("/bookingCancelled")
	public Response BookingCancelled(BookingCancelled event) {
		Logger.getAnonymousLogger().info("Received event: " + event);
		bookingProjection.processBookingCancelledEvent(event);
		return Response.ok(event).build();
	}

	@GET
	@Path("/bookings")
	public Response getAllBookings() {
		List<BookingQueryPanacheModel> bookings = bookingService.getAllBookings();
		return Response.ok(bookings).build();
	}

	@GET
	@Path("/bookings/{bookingId}")
	public Response getBookingById(@PathParam("bookingId") String bookingId) {
		BookingQueryPanacheModel booking = bookingService.getBookingById(bookingId);
		if (booking == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Booking not found").build();
		}
		return Response.ok(booking).build();
	}

	@GET
	@Path("/bookings/customer/{customerId}")
	public Response getBookingsByCustomerId(@PathParam("customerId") String customerId) {
		List<BookingQueryPanacheModel> bookings = bookingService.getBookingsByCustomerId(customerId);
		return Response.ok(bookings).build();
	}

	@GET
	@Path("/bookings/room/{roomId}")
	public Response getBookingsByRoomId(@PathParam("roomId") String roomId) {
		List<BookingQueryPanacheModel> bookings = bookingService.getBookingsByRoomId(roomId);
		return Response.ok(bookings).build();
	}

	@GET
	@Path("/bookings/date-range")
	public Response getBookingsByDateRange(@QueryParam("startDate") String startDateStr,
										   @QueryParam("endDate") String endDateStr) {
		try {
			LocalDate startDate = LocalDate.parse(startDateStr);
			LocalDate endDate = LocalDate.parse(endDateStr);

			List<GetBookingsDTO> bookings = bookingProjection.getBookingsByDateRange(startDate, endDate);
			return Response.ok(bookings).build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Error parsing dates: " + e.getMessage())
					.build();
		}
	}

	@GET
	@Path("/bookings/active")
	public Response getActiveBookings() {
		List<BookingQueryPanacheModel> bookings = bookingService.getActiveBookings();
		return Response.ok(bookings).build();
	}

	@GET
	@Path("/bookings/paid")
	public Response getPaidBookings() {
		List<BookingQueryPanacheModel> bookings = bookingService.getPaidBookings();
		return Response.ok(bookings).build();
	}
}