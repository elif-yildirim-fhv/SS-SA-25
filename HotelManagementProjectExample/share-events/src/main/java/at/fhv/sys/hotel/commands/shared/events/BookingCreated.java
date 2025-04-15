package at.fhv.sys.hotel.commands.shared.events;

import java.time.LocalDate;

public class BookingCreated {
    private String bookingId;
    private String roomId;
    private String userId;
    private LocalDate startDate;
    private LocalDate endDate;

    public BookingCreated() {}

    public BookingCreated(String bookingId, String roomId, String userId, LocalDate startDate, LocalDate endDate) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "BookingCreated{" +
                "bookingId='" + bookingId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", userId='" + userId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
} 