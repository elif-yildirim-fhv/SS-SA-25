package at.fhv.sys.hotel.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class PaymentQueryModel extends PanacheEntity {
    public String paymentId;
    public String bookingId;
    public double amount;
    public LocalDateTime paymentDate;
    public String paymentMethod;
    public boolean isCompleted;

    public PaymentQueryModel() {}

    public PaymentQueryModel(String paymentId, String bookingId, double amount, LocalDateTime paymentDate, String paymentMethod) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.isCompleted = false;
    }

    public enum PaymentMethod {
        CREDIT_CARD,
        CASH,
        BANK_TRANSFER,
        PAYPAL
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
} 