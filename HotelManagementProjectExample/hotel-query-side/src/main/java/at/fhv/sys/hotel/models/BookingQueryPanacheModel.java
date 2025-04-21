package at.fhv.sys.hotel.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
public class BookingQueryPanacheModel extends PanacheEntity {
    public String bookingId;
    public String roomId;
    public String customerId;
    public LocalDate startDate;
    public LocalDate endDate;
    public double totalPrice;
    public boolean isPaid;
    public boolean isCancelled;

    public BookingQueryPanacheModel() {
    }

    public BookingQueryPanacheModel(String bookingId, String roomId, String customerId, LocalDate startDate, LocalDate endDate, double totalPrice) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.isPaid = false;
        this.isCancelled = false;
    }

    public static BookingQueryPanacheModel findByBookingId(String bookingId) {
        return find("bookingId", bookingId).firstResult();
    }

    public static List<BookingQueryPanacheModel> findByCustomerId(String customerId) {
        return find("customerId", customerId).list();
    }

    public static List<BookingQueryPanacheModel> findByRoomId(String roomId) {
        return find("roomId", roomId).list();
    }

    public static List<BookingQueryPanacheModel> findByDateRange(LocalDate start, LocalDate end) {
        return find("startDate >= ?1 and endDate <= ?2", start, end).list();
    }

    public static List<BookingQueryPanacheModel> findActiveBookings() {
        return find("isCancelled = false").list();
    }

    public static List<BookingQueryPanacheModel> findPaidBookings() {
        return find("isPaid = true").list();
    }
} 