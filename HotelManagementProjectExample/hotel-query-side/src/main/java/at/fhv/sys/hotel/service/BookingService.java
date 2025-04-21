package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.BookingQueryModel;
import at.fhv.sys.hotel.models.BookingQueryPanacheModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class BookingService {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void createBooking(BookingQueryPanacheModel booking) {
        entityManager.persist(booking);
    }

    @Transactional
    public void updateBooking(BookingQueryPanacheModel booking) {
        BookingQueryPanacheModel existingBooking = getBookingById(booking.bookingId);
        if (existingBooking != null) {
            existingBooking.isPaid = booking.isPaid;
            existingBooking.isCancelled = booking.isCancelled;
            entityManager.merge(existingBooking);
        }
    }

    @Transactional
    public void cancelBooking(String bookingId) {
        BookingQueryPanacheModel booking = getBookingById(bookingId);
        if (booking != null) {
            booking.isCancelled = true;
            entityManager.merge(booking);
        }
    }

    public List<BookingQueryModel> getAllBookings() {
        return entityManager.createQuery("SELECT r FROM BookingQueryModel r", BookingQueryModel.class).getResultList();
    }

    public BookingQueryPanacheModel getBookingById(String bookingId) {
        return entityManager.createQuery(
            "SELECT b FROM BookingQueryModel b WHERE b.bookingId = :bookingId",
            BookingQueryPanacheModel.class
        )
        .setParameter("bookingId", bookingId)
        .getSingleResult();
    }

    public List<BookingQueryPanacheModel> getAvailableBookings() {
        return entityManager.createQuery(
            "SELECT b FROM BookingQueryPanacheModel b WHERE b.isCancelled = false",
            BookingQueryPanacheModel.class
        )
        .getResultList();
    }
} 