package at.fhv.sys.hotel.commands;

import at.fhv.sys.hotel.client.EventBusClient;
import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.domain.Booking;
import at.fhv.sys.hotel.domain.Room;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ApplicationScoped
public class BookingAggregate {
	private final Map<String, Booking> bookings = new HashMap<>();
	private final Map<String, Room> rooms = new HashMap<>();

	@Inject
	@RestClient
	EventBusClient eventClient;

	public String handle(BookRoomCommand command) {
		Room room = rooms.get(command.roomId());
		if (room == null) {
			throw new IllegalStateException("Room not found");
		}

		if (!room.isAvailable()) {
			throw new IllegalStateException("Room is not available");
		}

		Booking booking = new Booking(
			command.roomId(),
			command.userId(),
			command.startDate(),
			command.endDate()
		);

		booking.calculateTotalPrice(room.getPrice());
		bookings.put(booking.getId(), booking);
		room.setAvailable(false);

		BookingCreated event = new BookingCreated(
			booking.getId(),
			booking.getRoomId(),
			booking.getUserId(),
			booking.getStartDate(),
			booking.getEndDate()
		);

		Logger.getAnonymousLogger().info(eventClient.processBookingCreatedEvent(event).toString());
		return booking.getId();
	}
}

