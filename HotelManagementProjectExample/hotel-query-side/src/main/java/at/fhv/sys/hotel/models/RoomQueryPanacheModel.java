package at.fhv.sys.hotel.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class RoomQueryPanacheModel extends PanacheEntity {
    public String roomId;
    public String roomNumber;
    public double price;
    public int maxCapacity;
    public boolean isAvailable;
    public String roomType;

    public RoomQueryPanacheModel() {}

    public RoomQueryPanacheModel(String roomId, String roomNumber, double price, int maxCapacity) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.price = price;
        this.maxCapacity = maxCapacity;
        this.isAvailable = true;
    }

    public static RoomQueryPanacheModel findByRoomId(String roomId) {
        return find("roomId", roomId).firstResult();
    }

    public static RoomQueryPanacheModel findByRoomNumber(String roomNumber) {
        return find("roomNumber", roomNumber).firstResult();
    }

    public static List<RoomQueryPanacheModel> findByRoomType(String roomType) {
        return find("roomType", roomType).list();
    }


}
