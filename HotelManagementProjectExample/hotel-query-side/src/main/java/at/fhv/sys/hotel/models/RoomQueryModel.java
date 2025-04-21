package at.fhv.sys.hotel.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class RoomQueryModel extends PanacheEntity {
    public String roomId;
    public String roomNumber;
    public double price;
    public int maxCapacity;
    public boolean isAvailable;
    public String roomType;
} 