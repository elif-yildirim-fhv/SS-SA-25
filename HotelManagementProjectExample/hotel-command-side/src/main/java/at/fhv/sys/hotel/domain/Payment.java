package at.fhv.sys.hotel.domain;

import java.time.LocalDateTime;

public class Payment {
    private String id;
    private String bookingId;
    private double amount;
    private LocalDateTime paymentDate;
    private PaymentMethod paymentMethod;
    private boolean isCompleted;

    public Payment(String bookingId, double amount, PaymentMethod paymentMethod) {
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
        this.paymentDate = LocalDateTime.now();
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

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method is required");
        }
        this.paymentMethod = paymentMethod;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public enum PaymentMethod {
        CREDIT_CARD,
        CASH,
        BANK_TRANSFER,
        PAYPAL
    }
} 