package at.fhv.sys.hotel.commands.shared.events;

public class RoomCreated {
    private String roomId;
    private String roomNumber;
    private double price;
    private int maxCapacity;
    private boolean isAvailable;
    private String roomType;

    public RoomCreated() {}

    public RoomCreated(String roomId, String roomNumber, double price, int maxCapacity, boolean isAvailable, String roomType) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.price = price;
        this.maxCapacity = maxCapacity;
        this.isAvailable = isAvailable;
        this.roomType = roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "RoomCreated{" +
                "roomId='" + roomId + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", price=" + price +
                ", maxCapacity=" + maxCapacity +
                ", isAvailable=" + isAvailable +
                ", roomType=" + roomType +
                '}';
    }

    public enum RoomType {
        SINGLE,
        DOUBLE,
        SUITE,
        FAMILY
    }
} 