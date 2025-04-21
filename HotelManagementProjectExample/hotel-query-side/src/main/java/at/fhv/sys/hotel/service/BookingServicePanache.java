package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.BookingQueryPanacheModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class BookingServicePanache {

    @Inject
    EntityManager entityManager;

    public List<BookingQueryPanacheModel> getAllBookings() {
        return BookingQueryPanacheModel.listAll();
    }

    @Transactional
    public void createBooking(BookingQueryPanacheModel booking) {
        booking.persist();
    }

    @Transactional
    public void cancelBooking(String bookingId) {
        BookingQueryPanacheModel booking = BookingQueryPanacheModel.findByBookingId(bookingId);
        if (booking != null) {
            booking.isCancelled = true;
            booking.persist();
        }
    }

    public List<BookingQueryPanacheModel> getBookingsByDateRange(LocalDate start, LocalDate end) {
        return BookingQueryPanacheModel.findByDateRange(start, end);
    }

    public BookingQueryPanacheModel getBookingById(String bookingId) {
        return BookingQueryPanacheModel.findByBookingId(bookingId);
    }

    public List<BookingQueryPanacheModel> getBookingsByCustomerId(String customerId) {
        return BookingQueryPanacheModel.findByCustomerId(customerId);
    }

    public List<BookingQueryPanacheModel> getBookingsByRoomId(String roomId) {
        return BookingQueryPanacheModel.findByRoomId(roomId);
    }

    public List<BookingQueryPanacheModel> getActiveBookings() {
        return BookingQueryPanacheModel.findActiveBookings();
    }

    public List<BookingQueryPanacheModel> getPaidBookings() {
        return BookingQueryPanacheModel.findPaidBookings();
    }

    @Transactional
    public void updateBooking(BookingQueryPanacheModel booking) {
        BookingQueryPanacheModel existingBooking = BookingQueryPanacheModel.findByBookingId(booking.bookingId);
        if (existingBooking != null) {
            existingBooking.roomId = booking.roomId;
            existingBooking.customerId = booking.customerId;
            existingBooking.startDate = booking.startDate;
            existingBooking.endDate = booking.endDate;
            existingBooking.totalPrice = booking.totalPrice;
            existingBooking.isPaid = booking.isPaid;
            existingBooking.isCancelled = booking.isCancelled;
            existingBooking.persist();
        }
    }

    @Transactional
    public void deleteBooking(String bookingId) {
        BookingQueryPanacheModel booking = BookingQueryPanacheModel.findByBookingId(bookingId);
        if (booking != null) {
            booking.delete();
        }
    }

    public boolean bookingExists(String bookingId) {
        return BookingQueryPanacheModel.findByBookingId(bookingId) != null;
    }
} 