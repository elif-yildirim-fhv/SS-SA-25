package at.fhv.sys.hotel.projection;

import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.BookingCancelled;
import at.fhv.sys.hotel.commands.shared.events.PaymentReceived;
import at.fhv.sys.hotel.models.BookingQueryModel;
import at.fhv.sys.hotel.models.RoomAvailabilityModel;
import at.fhv.sys.hotel.service.BookingService;
import at.fhv.sys.hotel.service.RoomAvailabilityService;
import at.fhv.sys.hotel.DTO.GetBookingsDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.logging.Logger;

@ApplicationScoped
public class BookingProjection implements Projection {
	private static final Logger LOGGER = Logger.getLogger(BookingProjection.class.getName());

	@Inject
	BookingService bookingService;

	@Inject
	RoomAvailabilityService roomAvailabilityService;

	public BookingQueryModel getBookingById(String bookingId) {
		return bookingService.findById(bookingId);
	}

	public List<BookingQueryModel> getAllBookings() {
		return bookingService.findAll();
	}

	public List<BookingQueryModel> getBookingsByCustomerId(String customerId) {
		return bookingService.findByCustomerId(customerId);
	}

	public List<BookingQueryModel> getBookingsByRoomId(String roomId) {
		return bookingService.findByRoomId(roomId);
	}

	public List<GetBookingsDTO> getBookingsByDateRange(LocalDate startDate, LocalDate endDate) {
		List<BookingQueryModel> bookings = bookingService.findByDateRange(startDate, endDate);
		return convertToGetBookingsDTO(bookings);
	}

	private List<GetBookingsDTO> convertToGetBookingsDTO(List<BookingQueryModel> bookings) {
		return bookings.stream()
			.map(booking -> {
				Set<String> rooms = new HashSet<>();
				rooms.add(booking.getRoomId());
				
				return new GetBookingsDTO(
					booking.getBookingId(),
					rooms,
					booking.getCustomerId(),
					booking.getStartDate(),
					booking.getEndDate(),
					booking.getTotalPrice(),
					booking.isPaid(),
					booking.isCancelled()
				);
			})
			.collect(Collectors.toList());
	}

	public List<BookingQueryModel> getActiveBookings() {
		return bookingService.findActiveBookings();
	}

	public List<BookingQueryModel> getCancelledBookings() {
		return bookingService.findCancelledBookings();
	}

	public List<BookingQueryModel> getUnpaidBookings() {
		return bookingService.findUnpaidBookings();
	}

	@Override
	public void clearState() {
		try {
			bookingService.deleteAll();
			roomAvailabilityService.deleteAll();
		} catch (Exception e) {
			LOGGER.severe("Error clearing booking state: " + e.getMessage());
			throw e;
		}
	}

	@Override
	@Transactional
	public void processEvent(Object event) {
		if (event instanceof BookingCreated) {
			processBookingCreatedEvent((BookingCreated) event);
		} else if (event instanceof BookingCancelled) {
			processBookingCancelledEvent((BookingCancelled) event);
		} else if (event instanceof PaymentReceived) {
			processPaymentReceivedEvent((PaymentReceived) event);
		}
	}

	@Transactional
	public void processBookingCreatedEvent(BookingCreated event) {
		try {
			LOGGER.info("Processing BookingCreated event: " + event);

			BookingQueryModel booking = new BookingQueryModel(
				event.getBookingId(),
				event.getCustomerId(),
				event.getRoomId(),
				event.getStartDate(),
				event.getEndDate(),
				event.getTotalPrice(),
				false
			);
			bookingService.createBooking(booking);

			RoomAvailabilityModel availability = new RoomAvailabilityModel(
				event.getRoomId(),
				event.getStartDate(),
				event.getEndDate()
			);
			roomAvailabilityService.addAvailability(availability);

			LOGGER.info("Successfully processed BookingCreated event for booking: " + event.getBookingId());
		} catch (Exception e) {
			LOGGER.severe("Error processing BookingCreated event: " + e.getMessage());
			throw e;
		}
	}

	@Transactional
	public void processBookingCancelledEvent(BookingCancelled event) {
		try {
			LOGGER.info("Processing BookingCancelled event: " + event);

			BookingQueryModel booking = bookingService.findById(event.getBookingId());
			if (booking != null) {
				booking.setCancelled(true);
				bookingService.updateBooking(booking);

				roomAvailabilityService.removeAvailability(
					booking.getRoomId(),
					booking.getStartDate(),
					booking.getEndDate()
				);
			}

			LOGGER.info("Successfully processed BookingCancelled event for booking: " + event.getBookingId());
		} catch (Exception e) {
			LOGGER.severe("Error processing BookingCancelled event: " + e.getMessage());
			throw e;
		}
	}

	@Transactional
	public void processPaymentReceivedEvent(PaymentReceived event) {
		try {
			LOGGER.info("Processing PaymentReceived event: " + event);

			BookingQueryModel booking = bookingService.findById(event.getBookingId());
			if (booking != null) {
				booking.setPaid(true);
				bookingService.updateBooking(booking);
			}

			LOGGER.info("Successfully processed PaymentReceived event for booking: " + event.getBookingId());
		} catch (Exception e) {
			LOGGER.severe("Error processing PaymentReceived event: " + e.getMessage());
			throw e;
		}
	}
}
