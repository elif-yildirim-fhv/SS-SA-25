package at.fhv.sys.hotel.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Payment {
    private String id;
    private String bookingId;
    private double amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private boolean isCompleted;

    public Payment(String bookingId, double amount, String paymentMethod) {
        if (bookingId == null || bookingId.isEmpty()) {
            throw new IllegalArgumentException("Booking ID is required");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method is required");
        }

        this.id = java.util.UUID.randomUUID().toString();
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentDate = LocalDate.now();
        this.paymentMethod = paymentMethod;
        this.isCompleted = false;
    }

    public String getId() {
        return id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }


} 