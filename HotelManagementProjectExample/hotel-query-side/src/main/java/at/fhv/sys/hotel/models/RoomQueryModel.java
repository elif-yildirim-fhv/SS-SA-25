package at.fhv.sys.hotel.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RoomQueryModel{

    @Id
    private String roomId;
    private String roomNumber;
    private double price;
    private int maxCapacity;
    private boolean isAvailable;
    private String roomType;

    public RoomQueryModel() {}

    public RoomQueryModel(String roomId, String roomNumber, double price, int maxCapacity, boolean isAvailable, String roomType) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.price = price;
        this.maxCapacity = maxCapacity;
        this.isAvailable = isAvailable;
        this.roomType = roomType;
    }

    public enum RoomType {
        SINGLE,
        DOUBLE,
        SUITE,
        FAMILY
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
        isAvailable = available;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
