package at.fhv.sys.hotel.DTO;

public class FreeRoomsDTO {
    private String roomId;
    private String roomNumber;
    private double price;
    private int maxCapacity;
    private boolean isAvailable;
    private String roomType;

    public FreeRoomsDTO(String roomId, String roomNumber, double price, int maxCapacity, boolean isAvailable, String roomType) {
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

    public String getRoomType() {
        return roomType;
    }
}
