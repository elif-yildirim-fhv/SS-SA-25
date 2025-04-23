package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.BookingQueryModel;
import at.fhv.sys.hotel.models.BookingQueryPanacheModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class BookingService {
    private static final Logger LOGGER = Logger.getLogger(BookingService.class.getName());

    @Inject
    EntityManager entityManager;

    public BookingQueryModel findById(String bookingId) {
        return entityManager.find(BookingQueryModel.class, bookingId);
    }

    public List<BookingQueryModel> findAll() {
        return entityManager.createQuery("SELECT b FROM BookingQueryModel b", BookingQueryModel.class)
            .getResultList();
    }

    public List<BookingQueryModel> findByCustomerId(String customerId) {
        return entityManager.createQuery(
            "SELECT b FROM BookingQueryModel b WHERE b.customerId = :customerId", 
            BookingQueryModel.class)
            .setParameter("customerId", customerId)
            .getResultList();
    }

    public List<BookingQueryModel> findByRoomId(String roomId) {
        return entityManager.createQuery(
            "SELECT b FROM BookingQueryModel b WHERE b.roomId = :roomId", 
            BookingQueryModel.class)
            .setParameter("roomId", roomId)
            .getResultList();
    }

    public List<BookingQueryModel> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return entityManager.createQuery(
            "SELECT b FROM BookingQueryModel b WHERE b.startDate >= :startDate AND b.endDate <= :endDate", 
            BookingQueryModel.class)
            .setParameter("startDate", startDate)
            .setParameter("endDate", endDate)
            .getResultList();
    }

    public List<BookingQueryModel> findActiveBookings() {
        return entityManager.createQuery(
            "SELECT b FROM BookingQueryModel b WHERE b.cancelled = false", 
            BookingQueryModel.class)
            .getResultList();
    }

    public List<BookingQueryModel> findCancelledBookings() {
        return entityManager.createQuery(
            "SELECT b FROM BookingQueryModel b WHERE b.cancelled = true", 
            BookingQueryModel.class)
            .getResultList();
    }

    public List<BookingQueryModel> findUnpaidBookings() {
        return entityManager.createQuery(
            "SELECT b FROM BookingQueryModel b WHERE b.paid = false AND b.cancelled = false",
            BookingQueryModel.class)
            .getResultList();
    }

    @Transactional
    public void createBooking(BookingQueryModel booking) {
        entityManager.persist(booking);
    }

    @Transactional
    public void updateBooking(BookingQueryModel booking) {
        entityManager.merge(booking);
    }

    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM BookingQueryModel").executeUpdate();
    }

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
        return entityManager.createQuery("SELECT b FROM BookingQueryModel b", BookingQueryModel.class).getResultList();
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