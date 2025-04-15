package at.fhv.sys.hotel.domain;

public class Room {
    private String id;
    private String roomNumber;
    private double price;
    private int maxCapacity;
    private boolean isAvailable;
    private boolean isSeaside;

    public Room(String roomNumber, double price, int maxCapacity, boolean isSeaside) {
        this.id = java.util.UUID.randomUUID().toString();
        this.roomNumber = roomNumber;
        this.price = price;
        this.maxCapacity = maxCapacity;
        this.isAvailable = true;
        this.isSeaside = isSeaside;
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

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean isSeaside() {
        return isSeaside;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
} 