package at.fhv.sys.hotel.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "payment_query")
public class PaymentQueryPanacheModel extends PanacheEntity {
    public String paymentId;
    public String bookingId;
    public double amount;
    public LocalDateTime paymentDate;
    public String paymentMethod;
    public boolean isCompleted;

    public PaymentQueryPanacheModel() {
    }

    public PaymentQueryPanacheModel(String paymentId, String bookingId, double amount, 
                                  String paymentMethod, LocalDateTime paymentDate) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
} 