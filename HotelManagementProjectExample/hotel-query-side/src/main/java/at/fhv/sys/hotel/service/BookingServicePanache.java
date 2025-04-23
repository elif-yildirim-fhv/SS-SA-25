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
        BookingQueryPanacheModel booking = getBookingById(bookingId);
        if (booking != null) {
            booking.isCancelled = true;
            booking.persist();
        }
    }

    @Transactional
    public void updateBooking(BookingQueryPanacheModel booking) {
        booking.isPersistent();
    }

    @Transactional
    public void deleteAll() {
        BookingQueryPanacheModel.deleteAll();
    }

    public BookingQueryPanacheModel getBookingById(String bookingId) {
        return BookingQueryPanacheModel.find("bookingId", bookingId).firstResult();
    }

    public List<BookingQueryPanacheModel> getBookingsByDateRange(LocalDate startDate, LocalDate endDate) {
        return BookingQueryPanacheModel.find(
            "startDate >= ?1 and endDate <= ?2",
            startDate, endDate
        ).list();
    }

    public List<BookingQueryPanacheModel> getBookingsByCustomerId(String customerId) {
        return BookingQueryPanacheModel.find(
            "customerId = ?1",
            customerId
        ).list();
    }

    public List<BookingQueryPanacheModel> getActiveBookings() {
        return BookingQueryPanacheModel.find(
            "isCancelled = false and endDate >= ?1",
            LocalDate.now()
        ).list();
    }

    public List<BookingQueryPanacheModel> findCancelledBookings() {
        return BookingQueryPanacheModel.find(
            "isCancelled = true"
        ).list();
    }

    public List<BookingQueryPanacheModel> findUnpaidBookings() {
        return BookingQueryPanacheModel.find(
            "isPaid = false and isCancelled = false"
        ).list();
    }

    public List<BookingQueryPanacheModel> getBookingsByRoomId(String roomId) {
        return BookingQueryPanacheModel.findByRoomId(roomId);
    }

    public List<BookingQueryPanacheModel> getPaidBookings() {
        return BookingQueryPanacheModel.findPaidBookings();
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