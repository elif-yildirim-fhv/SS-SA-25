package at.fhv.sys.hotel.query.controller;

import at.fhv.sys.hotel.DTO.FreeRoomsDTO;
import at.fhv.sys.hotel.DTO.GetBookingsDTO;
import at.fhv.sys.hotel.DTO.GetCustomerDTO;
import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.BookingCancelled;
import at.fhv.sys.hotel.projection.HotelProjection;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logmanager.Logger;

import java.time.LocalDate;
import java.util.List;

@Path("/api/hotel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HotelQueryController {

    private static final Logger LOGGER = Logger.getLogger(HotelQueryController.class.getName());

    @Inject
    HotelProjection hotelProjection;

    public HotelQueryController() {
    }

    @POST
    @Path("/events/booking-created")
    public Response handleBookingCreated(BookingCreated event) {
        LOGGER.info("Received BookingCreated event: " + event);
        hotelProjection.processIncomingBookingCreatedEvent(event);
        return Response.ok().build();
    }

    @POST
    @Path("/events/booking-cancelled")
    public Response handleBookingCancelled(BookingCancelled event) {
        LOGGER.info("Received BookingCancelled event: " + event);
        hotelProjection.processIncomingBookingCanceledEvent(event);
        return Response.ok().build();
    }

    // GetBookings (mit Zeitraum)
    @GET
    @Path("/bookings")
    public Response getBookings(
            @QueryParam("startDate") String startDateStr,
            @QueryParam("endDate") String endDateStr) {
        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);
            
            List<GetBookingsDTO> bookings = hotelProjection.getBookingsByTimeRange(startDate, endDate);
            return Response.ok(bookings).build();
        } catch (Exception e) {
            LOGGER.severe("Error getting bookings: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error parsing dates: " + e.getMessage())
                    .build();
        }
    }

    // GetFreeRooms (mit Zeitraum und Personenanzahl)
    @GET
    @Path("/rooms/free")
    public Response getFreeRooms(
            @QueryParam("startDate") String startDateStr,
            @QueryParam("endDate") String endDateStr,
            @QueryParam("persons") int persons) {
        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);
            
            List<FreeRoomsDTO> freeRooms = hotelProjection.getFreeRooms(startDate, endDate, persons);
            return Response.ok(freeRooms).build();
        } catch (Exception e) {
            LOGGER.severe("Error getting free rooms: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error: " + e.getMessage())
                    .build();
        }
    }

    // GetCustomers (mit optionalem Namensparameter)
    @GET
    @Path("/customers")
    public Response getCustomers(@QueryParam("name") String name) {
        try {
            List<GetCustomerDTO> customers = hotelProjection.getCustomers(name);
            return Response.ok(customers).build();
        } catch (Exception e) {
            LOGGER.severe("Error getting customers: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error getting customers: " + e.getMessage())
                    .build();
        }
    }
} 