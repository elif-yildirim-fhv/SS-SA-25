package at.fhv.sys.hotel.query.controller.room;

import at.fhv.sys.hotel.projection.room.RoomProjection;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logmanager.Logger;
import java.time.LocalDate;
import java.util.List;

@Path("/api/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomQueryController {
    private static final Logger LOGGER = Logger.getLogger(RoomQueryController.class.getName());

    @Inject
    RoomProjection roomProjection;

    @GET
    @Path("/free")
    public Response getFreeRooms(
        @QueryParam("startDate") String startDateStr,
        @QueryParam("endDate")   String endDateStr,
        @QueryParam("persons")   int persons) {

        LocalDate start = LocalDate.parse(startDateStr);
        LocalDate end   = LocalDate.parse(endDateStr);

        List<?> rooms = roomProjection.getFree(start, end, persons);
        return Response.ok(rooms).build();
    }
} 