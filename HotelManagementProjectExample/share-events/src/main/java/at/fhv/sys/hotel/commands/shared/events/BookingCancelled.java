package at.fhv.sys.hotel.commands.shared.events;

public class BookingCancelled {
    private String bookingId;
    private String roomId;
    private String customerId;

    public BookingCancelled() {}

    public BookingCancelled(String bookingId, String roomId, String customerId) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.customerId = customerId;
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

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "BookingCancelled{" +
                "bookingId='" + bookingId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
} 