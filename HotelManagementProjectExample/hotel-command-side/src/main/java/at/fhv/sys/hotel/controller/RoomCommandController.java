package at.fhv.sys.hotel.controller;

import at.fhv.sys.hotel.commands.BookingAggregate;
import at.fhv.sys.hotel.domain.Room;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.util.Map;

@Path("/api/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Room Commands", description = "Endpoints for room-related commands")
public class RoomCommandController {

    @Inject
    BookingAggregate bookingAggregate;

    @POST
    @Path("/roomCreated")
    @Operation(
            summary = "Create a new room",
            description = "Creates a new room with the specified details and returns the room ID"
    )
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Room created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON)
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Invalid room data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON)
            )
    })
    public Response createRoom(
            @Parameter(description = "Room number", required = true, example = "101")
            @QueryParam("roomNumber") String roomNumber,

            @Parameter(description = "Price per night", required = true, example = "150.0")
            @QueryParam("price") double price,

            @Parameter(description = "Maximum capacity", required = true, example = "2")
            @QueryParam("maxCapacity") int maxCapacity,

            @Parameter(description = "Room type (SINGLE, DOUBLE, SUITE, FAMILY)", required = true, example = "DOUBLE")
            @QueryParam("roomType") String roomType) {

        try {
            if (roomNumber == null || roomNumber.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("error", "Room number is required"))
                        .build();
            }

            if (price <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("error", "Price must be positive"))
                        .build();
            }

            if (maxCapacity <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("error", "Max capacity must be positive"))
                        .build();
            }

            Room room = new Room(roomNumber, price, maxCapacity, roomType);
            bookingAggregate.addRoom(room);

            return Response.ok(Map.of("message", "Room created successfully", "roomId", room.getId())).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Error creating room: " + e.getMessage()))
                    .build();
        }
    }

}