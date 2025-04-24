package at.fhv.sys.hotel.query.controller;

import at.fhv.sys.hotel.commands.shared.events.RoomCreated;
import at.fhv.sys.hotel.models.RoomQueryPanacheModel;
import at.fhv.sys.hotel.projection.RoomProjection;
import at.fhv.sys.hotel.service.RoomService;
import at.fhv.sys.hotel.DTO.FreeRoomsDTO;
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

	@Inject
	RoomProjection roomProjection;

	@Inject
	RoomService roomService;

	@POST
	@Path("/roomCreated")
	public Response roomCreated(RoomCreated event) {
		Logger.getAnonymousLogger().info("Received event: " + event);
		roomProjection.processRoomCreatedEvent(event);
		return Response.ok(event).build();
	}

	@GET
	public Response getAllRooms() {
		return Response.ok(roomService.getAllRooms()).build();
	}

	@GET
	@Path("/{roomId}")
	public Response getRoomById(@PathParam("roomId") String roomId) {
		return Response.ok(roomService.getRoomById(roomId)).build();
	}

	@GET
	@Path("/available")
	public Response getAvailableRooms() {
		return Response.ok(roomService.getAvailableRooms()).build();
	}

	@GET
	@Path("/type/{roomType}")
	public Response getRoomsByType(@PathParam("roomType") String roomType) {
		return Response.ok(roomService.getRoomsByType(roomType)).build();
	}

	@GET
	@Path("/capacity/{minCapacity}")
	public Response getRoomsByCapacity(@PathParam("minCapacity") int minCapacity) {
		return Response.ok(roomService.getRoomsByCapacity(minCapacity)).build();
	}

	@GET
	@Path("/free")
	public Response getFreeRooms(@QueryParam("startDate") String startDateStr,
								 @QueryParam("endDate") String endDateStr,
								 @QueryParam("persons") int persons) {
		try {
			LocalDate startDate = LocalDate.parse(startDateStr);
			LocalDate endDate = LocalDate.parse(endDateStr);

			List<FreeRoomsDTO> freeRooms = roomProjection.getAvailableRooms(startDate, endDate, persons);
			return Response.ok(freeRooms).build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Error: " + e.getMessage())
					.build();
		}
	}
}