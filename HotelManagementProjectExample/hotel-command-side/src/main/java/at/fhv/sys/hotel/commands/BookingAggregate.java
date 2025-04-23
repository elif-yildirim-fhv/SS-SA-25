package at.fhv.sys.hotel.commands;

import at.fhv.sys.hotel.client.EventBusClient;
import at.fhv.sys.hotel.commands.shared.events.*;
import at.fhv.sys.hotel.domain.Booking;
import at.fhv.sys.hotel.domain.Payment;
import at.fhv.sys.hotel.domain.Room;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ApplicationScoped
public class BookingAggregate {
    private final Map<String, Booking> bookings = new HashMap<>();
    private final Map<String, Payment> payments = new HashMap<>();
    private final Map<String, Room> rooms = new HashMap<>();

    @Inject
    @RestClient
    private EventBusClient eventClient;

    private static final Logger LOGGER = Logger.getLogger(BookingAggregate.class.getName());

    public String handle(BookRoomCommand command) {
        try {
            Room room = rooms.get(command.roomId());
            if (room == null) {
                throw new IllegalArgumentException("Room not found");
            }

            if (!isRoomAvailable(command.roomId(), command.startDate(), command.endDate())) {
                throw new IllegalStateException("Room is not available for the selected dates");
            }

            Booking booking = new Booking(
                command.roomId(),
                command.customerId(),
                command.startDate(),
                command.endDate()
            );

            booking.calculateTotalPrice(room.getPrice());
            booking.setPaid(command.isPaid());
            if (command.isCancelled()) {
                booking.cancel();
            }
            
            bookings.put(booking.getId(), booking);
            room.setAvailable(false);

            BookingCreated event = new BookingCreated(
                booking.getId(),
                booking.getRoomId(),
                booking.getCustomerId(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getTotalPrice(),
                booking.isPaid(),
                booking.isCancelled()
            );

            eventClient.processBookingCreatedEvent(event);
            LOGGER.info("Booking created successfully with ID: " + booking.getId());

            return booking.getId();
        } catch (IllegalArgumentException | IllegalStateException e) {
            LOGGER.severe("Failed to create booking: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.severe("Unexpected error while creating booking: " + e.getMessage());
            throw new RuntimeException("Failed to create booking", e);
        }
    }

    public void handle(CancelBookingCommand command) {
        try {
            Booking booking = bookings.get(command.bookingId());
            if (booking == null) {
                throw new IllegalArgumentException("Booking not found");
            }

            Room room = rooms.get(booking.getRoomId());
            if (room != null) {
                room.setAvailable(true);
            }

            booking.cancel();

            BookingCancelled event = new BookingCancelled(
                booking.getId(),
                booking.getRoomId(),
                booking.getCustomerId()
            );

            eventClient.processBookingCancelledEvent(event);
            LOGGER.info("Booking cancelled successfully: " + booking.getId());
        } catch (Exception e) {
            LOGGER.severe("Failed to cancel booking: " + e.getMessage());
            throw e;
        }
    }

    public String handle(PayBookingCommand command) {
        try {
            Booking booking = bookings.get(command.bookingId());
            if (booking == null) {
                throw new IllegalArgumentException("Booking not found");
            }
            if (booking.isCancelled()) {
                throw new IllegalStateException("Cannot pay for cancelled booking");
            }
            if (booking.isPaid()) {
                throw new IllegalStateException("Booking is already paid");
            }

            Payment payment = new Payment(
                booking.getId(),
                booking.getTotalPrice(),
                Payment.PaymentMethod.valueOf(command.paymentMethod())
            );

            payments.put(payment.getId(), payment);
            booking.setPaid(true);

            PaymentReceived event = new PaymentReceived(
                payment.getId(),
                booking.getId(),
                payment.getAmount(),
                payment.getPaymentMethod().name(),
                payment.getPaymentDate()
            );

            eventClient.processPaymentReceivedEvent(event);
            LOGGER.info("Payment processed successfully for booking: " + booking.getId());

            return payment.getId();
        } catch (Exception e) {
            LOGGER.severe("Failed to process payment: " + e.getMessage());
            throw e;
        }
    }

    private boolean isRoomAvailable(String roomId, LocalDate startDate, LocalDate endDate) {
        Room room = rooms.get(roomId);
        if (room == null || !room.isAvailable()) {
            return false;
        }

        return bookings.values().stream()
            .filter(booking -> booking.getRoomId().equals(roomId))
            .filter(booking -> !booking.isCancelled())
            .noneMatch(booking ->
                (startDate.isBefore(booking.getEndDate()) || startDate.isEqual(booking.getEndDate())) &&
                (endDate.isAfter(booking.getStartDate()) || endDate.isEqual(booking.getStartDate()))
            );
    }

    public Booking getBooking(String bookingId) {
        return bookings.get(bookingId);
    }

    public Payment getPayment(String paymentId) {
        return payments.get(paymentId);
    }

    public void addRoom(Room room) {
        rooms.put(room.getId(), room);
        
        try {
            RoomCreated event = new RoomCreated(
                room.getId(),
                room.getRoomNumber(),
                room.getPrice(),
                room.getMaxCapacity(),
                room.isAvailable(),
                room.getRoomType()
            );
            
            eventClient.processRoomCreatedEvent(event);
            LOGGER.info("Room created successfully with ID: " + room.getId());
        } catch (Exception e) {
            LOGGER.severe("Failed to process room created event: " + e.getMessage());

        }
    }

    public Room getRoom(String roomId) {
        return rooms.get(roomId);
    }
}
