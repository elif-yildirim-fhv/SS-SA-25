package at.fhv.sys.hotel.domain;

public class Room {
    private String id;
    private String roomNumber;
    private double price;
    private int maxCapacity;
    private boolean isAvailable;
    private RoomType roomType;

    public Room(String roomNumber, double price, int maxCapacity, RoomType roomType) {
        if (roomNumber == null || roomNumber.isEmpty()) {
            throw new IllegalArgumentException("Room number is required");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        if (roomType == null) {
            throw new IllegalArgumentException("Room type is required");
        }

        this.id = java.util.UUID.randomUUID().toString();
        this.roomNumber = roomNumber;
        this.price = price;
        this.maxCapacity = maxCapacity;
        this.isAvailable = true;
        this.roomType = roomType;
    }

    public String getId() {
        return id;
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

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        if (roomType == null) {
            throw new IllegalArgumentException("Room type is required");
        }
        this.roomType = roomType;
    }

    public enum RoomType {
        SINGLE,
        DOUBLE,
        SUITE,
        FAMILY
    }
}
