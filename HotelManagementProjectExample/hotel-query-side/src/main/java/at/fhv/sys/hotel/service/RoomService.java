package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.RoomQueryModel;
import at.fhv.sys.hotel.models.RoomQueryPanacheModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class RoomService {

    @PersistenceContext
    EntityManager entityManager;

    public List<RoomQueryModel> getAllRooms() {
        return entityManager.createQuery("SELECT r FROM RoomQueryModel r", RoomQueryModel.class).getResultList();
    }

    @Transactional
    public void createRoom(RoomQueryPanacheModel room) {
        entityManager.persist(room);
    }

    @Transactional
    public void updateRoom(RoomQueryPanacheModel room) {
        RoomQueryPanacheModel existingRoom = entityManager.find(RoomQueryPanacheModel.class, room.id);
        if (existingRoom != null) {
            existingRoom.roomNumber = room.roomNumber;
            existingRoom.price = room.price;
            existingRoom.maxCapacity = room.maxCapacity;
            existingRoom.roomType = room.roomType;
            existingRoom.isAvailable = room.isAvailable;
            entityManager.merge(existingRoom);
        }
    }

    public RoomQueryModel getRoomById(String roomId) {
        return entityManager.createQuery(
            "SELECT r FROM RoomQueryModel r WHERE r.roomId = :roomId",
            RoomQueryModel.class
        )
        .setParameter("roomId", roomId)
        .getSingleResult();
    }

    public List<RoomQueryModel> getAvailableRooms() {
        return entityManager.createQuery(
            "SELECT r FROM RoomQueryModel r WHERE r.isAvailable = true",
            RoomQueryModel.class
        )
        .getResultList();
    }

    public List<RoomQueryModel> getRoomsByType(String roomType) {
        return entityManager.createQuery(
            "SELECT r FROM RoomQueryModel r WHERE r.roomType = :roomType",
            RoomQueryModel.class
        )
        .setParameter("roomType", roomType)
        .getResultList();
    }

    public List<RoomQueryModel> getRoomsByCapacity(int minCapacity) {
        return entityManager.createQuery(
            "SELECT r FROM RoomQueryModel r WHERE r.maxCapacity >= :minCapacity",
            RoomQueryModel.class
        )
        .setParameter("minCapacity", minCapacity)
        .getResultList();
    }
} 