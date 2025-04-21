package at.fhv.sys.hotel.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class PaymentQueryPanacheModel extends PanacheEntity {
    public String paymentId;
    public String bookingId;
    public double amount;
    public LocalDateTime paymentDate;
    public String paymentMethod;
    public boolean isCompleted;

    public PaymentQueryPanacheModel() {
    }

    public PaymentQueryPanacheModel(String paymentId, String bookingId, double amount, LocalDateTime paymentDate, String paymentMethod) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.isCompleted = false;
    }

    public static PaymentQueryPanacheModel findByPaymentId(String paymentId) {
        return find("paymentId", paymentId).firstResult();
    }

    public static List<PaymentQueryPanacheModel> findByBookingId(String bookingId) {
        return find("bookingId", bookingId).list();
    }

    public static List<PaymentQueryPanacheModel> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return find("paymentDate >= ?1 and paymentDate <= ?2", start, end).list();
    }

    public static List<PaymentQueryPanacheModel> findByPaymentMethod(String paymentMethod) {
        return find("paymentMethod", paymentMethod).list();
    }
} 