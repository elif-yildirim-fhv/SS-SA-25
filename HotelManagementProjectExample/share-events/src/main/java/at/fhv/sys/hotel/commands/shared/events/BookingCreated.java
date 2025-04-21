package at.fhv.sys.hotel.commands.shared.events;

import java.time.LocalDate;

public class BookingCreated {
    private String bookingId;
    private String roomId;
    private String customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;
    private boolean isPaid;
    private boolean isCancelled;

    public BookingCreated() {}

    public BookingCreated(String bookingId, String roomId, String customerId, LocalDate startDate, LocalDate endDate, double totalPrice, boolean isPaid, boolean isCancelled) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.isPaid = isPaid;
        this.isCancelled = isCancelled;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    @Override
    public String toString() {
        return "BookingCreated{" +
                "bookingId='" + bookingId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalPrice=" + totalPrice +
                ", isPaid=" + isPaid +
                ", isCancelled=" + isCancelled +
                '}';
    }
} 