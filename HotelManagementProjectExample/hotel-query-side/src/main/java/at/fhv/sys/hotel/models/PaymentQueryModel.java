package at.fhv.sys.hotel.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "payment_query")
public class PaymentQueryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String paymentId;
    private String bookingId;
    private double amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private boolean completed;

    public PaymentQueryModel() {
    }

    public PaymentQueryModel(String paymentId, String bookingId, double amount, 
                           String paymentMethod, LocalDate paymentDate, boolean completed) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.completed = completed;


    }

    public PaymentQueryModel(String paymentId, String bookingId, double amount,
                             LocalDate paymentDate, String paymentMethod) {
        this(paymentId, bookingId, amount, paymentMethod, paymentDate, true);
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

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
} 