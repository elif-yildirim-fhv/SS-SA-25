package at.fhv.sys.hotel.projection;

import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.BookingCancelled;
import at.fhv.sys.hotel.commands.shared.events.PaymentReceived;
import at.fhv.sys.hotel.models.BookingQueryModel;
import at.fhv.sys.hotel.models.BookingQueryPanacheModel;
import at.fhv.sys.hotel.service.BookingService;
import at.fhv.sys.hotel.service.BookingServicePanache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;

import java.util.logging.Logger;

@ApplicationScoped
public class BookingProjection {

	@Inject
	BookingService bookingService;

	@Inject
	BookingServicePanache bookingServicePanache;

	public void processBookingCreatedEvent(BookingCreated event) {
		try {
			Logger.getAnonymousLogger().info("Processing BookingCreated event: " + event);

			BookingQueryPanacheModel booking = new BookingQueryPanacheModel();
			booking.bookingId = event.getBookingId();
			booking.roomId = event.getRoomId();
			booking.customerId = event.getCustomerId();
			booking.startDate = event.getStartDate();
			booking.endDate = event.getEndDate();
			booking.totalPrice = event.getTotalPrice();
			booking.isPaid = false;
			booking.isCancelled = false;
			bookingService.createBooking(booking);

			BookingQueryPanacheModel bookingPanache = new BookingQueryPanacheModel(
				event.getBookingId(),
				event.getRoomId(),
				event.getCustomerId(),
				event.getStartDate(),
				event.getEndDate(),
				event.getTotalPrice()
			);
			bookingServicePanache.createBooking(bookingPanache);
			
			Logger.getAnonymousLogger().info("Successfully processed BookingCreated event for booking: " + event.getBookingId());
		} catch (PersistenceException e) {
			Logger.getAnonymousLogger().severe("Failed to persist booking: " + event.getBookingId() + ", error: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			Logger.getAnonymousLogger().severe("Unexpected error processing BookingCreated event: " + e.getMessage());
			throw e;
		}
	}

	public void processBookingCancelledEvent(BookingCancelled event) {
		try {
			Logger.getAnonymousLogger().info("Processing BookingCancelled event: " + event);

			bookingService.cancelBooking(event.getBookingId());
			bookingServicePanache.cancelBooking(event.getBookingId());
			
			Logger.getAnonymousLogger().info("Successfully processed BookingCancelled event for booking: " + event.getBookingId());
		} catch (PersistenceException e) {
			Logger.getAnonymousLogger().severe("Failed to cancel booking: " + event.getBookingId() + ", error: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			Logger.getAnonymousLogger().severe("Unexpected error processing BookingCancelled event: " + e.getMessage());
			throw e;
		}
	}

	public void processPaymentReceivedEvent(PaymentReceived event) {
		try {
			Logger.getAnonymousLogger().info("Processing PaymentReceived event: " + event);

			BookingQueryPanacheModel booking = bookingService.getBookingById(event.getBookingId());
			if (booking != null) {
				booking.isPaid = true;
				bookingService.updateBooking(booking);
			}

			BookingQueryPanacheModel bookingPanache = bookingServicePanache.getBookingById(event.getBookingId());
			if (bookingPanache != null) {
				bookingPanache.isPaid = true;
				bookingServicePanache.updateBooking(bookingPanache);
			}
			
			Logger.getAnonymousLogger().info("Successfully processed PaymentReceived event for booking: " + event.getBookingId());
		} catch (PersistenceException e) {
			Logger.getAnonymousLogger().severe("Failed to update payment status for booking: " + event.getBookingId() + ", error: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			Logger.getAnonymousLogger().severe("Unexpected error processing PaymentReceived event: " + e.getMessage());
			throw e;
		}
	}
}
