package at.fhv.sys.hotel.commands.shared.events;

import java.time.LocalDate;

public class BookingCreated {
    private String bookingId;
    private String roomId;
    private String customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;

    public BookingCreated() {}

    public BookingCreated(String bookingId, String roomId, String customerId, LocalDate startDate, LocalDate endDate, double totalPrice) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
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

    @Override
    public String toString() {
        return "BookingCreated{" +
                "bookingId='" + bookingId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalPrice=" + totalPrice +
                '}';
    }
} 