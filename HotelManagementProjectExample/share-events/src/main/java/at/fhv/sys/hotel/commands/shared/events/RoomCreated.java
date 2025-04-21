package at.fhv.sys.hotel.commands.shared.events;

public class RoomCreated {
    private String roomId;
    private String roomNumber;
    private double price;
    private int maxCapacity;
    private String roomType;
    private boolean isAvailable;

    public RoomCreated() {}

    public RoomCreated(String roomId, String roomNumber, double price, int maxCapacity, String roomType, boolean isAvailable) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.price = price;
        this.maxCapacity = maxCapacity;
        this.roomType = roomType;
        this.isAvailable = isAvailable;
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

    public String getRoomType() {
        return roomType;
    }

    public boolean isAvailable() {
        return isAvailable;
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

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "RoomCreated{" +
                "roomId='" + roomId + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", price=" + price +
                ", maxCapacity=" + maxCapacity +
                ", roomType='" + roomType + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
} 