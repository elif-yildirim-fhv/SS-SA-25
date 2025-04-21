package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.BookingQueryModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class BookingService {

    @Transactional
    public void createBooking(BookingQueryModel booking) {
        booking.persist();
    }

    @Transactional
    public void cancelBooking(String bookingId) {
        BookingQueryModel booking = BookingQueryModel.find("bookingId", bookingId).firstResult();
        if (booking != null) {
            booking.isCancelled = true;
            booking.persist();
        }
    }

    public List<BookingQueryModel> getAllBookings() {
        return BookingQueryModel.listAll();
    }

    public List<BookingQueryModel> getBookingsByDateRange(LocalDate start, LocalDate end) {
        return BookingQueryModel.find("startDate >= ?1 and endDate <= ?2", start, end).list();
    }

    public BookingQueryModel getBookingById(String bookingId) {
        return BookingQueryModel.find("bookingId", bookingId).firstResult();
    }
} 