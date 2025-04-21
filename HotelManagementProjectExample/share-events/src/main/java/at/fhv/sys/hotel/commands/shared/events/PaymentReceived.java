package at.fhv.sys.hotel.commands.shared.events;

import java.time.LocalDateTime;

public class PaymentReceived {
    private String paymentId;
    private String bookingId;
    private double amount;
    private String paymentMethod;
    private LocalDateTime paymentDate;

    public PaymentReceived() {}

    public PaymentReceived(String paymentId, String bookingId, double amount, String paymentMethod, LocalDateTime paymentDate) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
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

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "PaymentReceived{" +
                "paymentId='" + paymentId + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentDate=" + paymentDate +
                '}';
    }
} 