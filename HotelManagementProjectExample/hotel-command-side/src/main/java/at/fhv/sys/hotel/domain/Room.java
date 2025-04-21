package at.fhv.sys.hotel.domain;




public class Room {
    private String roomId;
    private String roomNumber;
    private double price;
    private int maxCapacity;
    private boolean isAvailable;
    private String roomType;

    public Room() {}

    public Room(String roomId, String roomNumber, double price, int maxCapacity, boolean isAvailable, String roomType) {
       this.roomId = java.util.UUID.randomUUID().toString();
        this.roomNumber = roomNumber;
        this.price = price;
        this.maxCapacity = maxCapacity;
        this.isAvailable = isAvailable;
        this.roomType = roomType;
    }

    public Room( String roomNumber, double price, int maxCapacity, String roomType) {
        this(null, roomNumber,price, maxCapacity, true, roomType);
    }

    public String getId() {
        return roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = price;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
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
}
