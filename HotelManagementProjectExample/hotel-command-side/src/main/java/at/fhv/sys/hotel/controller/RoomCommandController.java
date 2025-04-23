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

@Path("/api/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Room Commands", description = "Endpoints for room-related commands")
public class RoomCommandController {

    @Inject
    BookingAggregate bookingAggregate;

    @POST
    @Path("/create")
    @Operation(
            summary = "Create a new room",
            description = "Creates a new room with the specified details and returns the room ID"
    )
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Room created successfully",
                    content = @Content(mediaType = "text/plain")
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Invalid room data",
                    content = @Content(mediaType = "text/plain")
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
                        .entity("Room number is required")
                        .build();
            }

            if (price <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Price must be positive")
                        .build();
            }

            if (maxCapacity <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Max capacity must be positive")
                        .build();
            }

            Room room = new Room(roomNumber, price, maxCapacity, roomType);
            bookingAggregate.addRoom(room);

            return Response.ok("Room created with ID: " + room.getId()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error creating room: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{roomId}")
    @Operation(
            summary = "Get room by ID",
            description = "Retrieves room details by its unique identifier"
    )
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Room found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Room.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Room not found",
                    content = @Content(mediaType = "text/plain")
            )
    })
    public Response getRoom(
            @Parameter(description = "Room ID", required = true, example = "room-123")
            @PathParam("roomId") String roomId) {

        Room room = bookingAggregate.getRoom(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }
        return Response.ok(room).build();
    }

    @PUT
    @Path("/{roomId}/availability")
    @Operation(
            summary = "Update room availability",
            description = "Updates the availability status of a room"
    )
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Room availability updated",
                    content = @Content(mediaType = "text/plain")
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Room not found",
                    content = @Content(mediaType = "text/plain")
            )
    })
    public Response updateRoomAvailability(
            @Parameter(description = "Room ID", required = true, example = "room-123")
            @PathParam("roomId") String roomId,

            @Parameter(description = "Availability status", required = true, example = "true")
            @QueryParam("available") boolean available) {

        Room room = bookingAggregate.getRoom(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        room.setAvailable(available);
        return Response.ok("Room availability updated").build();
    }

    @PUT
    @Path("/{roomId}/price")
    @Operation(
            summary = "Update room price",
            description = "Updates the price per night for a room"
    )
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Room price updated",
                    content = @Content(mediaType = "text/plain")
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Room not found",
                    content = @Content(mediaType = "text/plain")
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Invalid price",
                    content = @Content(mediaType = "text/plain")
            )
    })
    public Response updateRoomPrice(
            @Parameter(description = "Room ID", required = true, example = "room-123")
            @PathParam("roomId") String roomId,

            @Parameter(description = "New price per night", required = true, example = "175.0")
            @QueryParam("price") double price) {

        try {
            Room room = bookingAggregate.getRoom(roomId);
            if (room == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Room not found")
                        .build();
            }

            room.setPrice(price);
            return Response.ok("Room price updated").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error updating room price: " + e.getMessage())
                    .build();
        }
    }
}