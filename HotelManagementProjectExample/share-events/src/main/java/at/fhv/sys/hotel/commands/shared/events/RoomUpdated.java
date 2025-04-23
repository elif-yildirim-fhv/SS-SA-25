package at.fhv.sys.hotel.commands.shared.events;

public class RoomUpdated {
    private String roomId;
    private String roomNumber;
    private double price;
    private int maxCapacity;
    private boolean available;
    private String roomType;

    public RoomUpdated() {}

    public RoomUpdated(String roomId, String roomNumber, double price, int maxCapacity, boolean available, String roomType) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.price = price;
        this.maxCapacity = maxCapacity;
        this.available = available;
        this.roomType = roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public double getPrice() {
        return price;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "RoomUpdated{" +
                "roomId='" + roomId + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", price=" + price +
                ", maxCapacity=" + maxCapacity +
                ", available=" + available +
                ", roomType='" + roomType + '\'' +
                '}';
    }
} 