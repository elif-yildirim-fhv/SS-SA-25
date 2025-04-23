package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.RoomQueryPanacheModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class RoomService {
    private static final Logger LOG = Logger.getLogger(RoomService.class);

    @PersistenceContext
    EntityManager entityManager;

    public List<RoomQueryPanacheModel> getAllRooms() {
        return RoomQueryPanacheModel.listAll();
    }

    @Transactional
    public void createRoom(RoomQueryPanacheModel room) {
        LOG.info("Creating room with ID: " + room.roomId);
        room.persist();
    }

    @Transactional
    public void updateRoom(RoomQueryPanacheModel room) {
        RoomQueryPanacheModel existingRoom = getRoomById(room.roomId);
        if (existingRoom != null) {
            existingRoom.roomNumber = room.roomNumber;
            existingRoom.price = room.price;
            existingRoom.maxCapacity = room.maxCapacity;
            existingRoom.roomType = room.roomType;
            existingRoom.isAvailable = room.isAvailable;
            existingRoom.persist();
        }
    }

    public RoomQueryPanacheModel getRoomById(String roomId) {
        return RoomQueryPanacheModel.findByRoomId(roomId);
    }

    public List<RoomQueryPanacheModel> getAvailableRooms() {
        return RoomQueryPanacheModel.find("isAvailable", true).list();
    }

    public List<RoomQueryPanacheModel> getRoomsByType(String roomType) {
        return RoomQueryPanacheModel.find("roomType", roomType).list();
    }

    public List<RoomQueryPanacheModel> getRoomsByCapacity(int minCapacity) {
        return RoomQueryPanacheModel.find("maxCapacity >= ?1", minCapacity).list();
    }

    public List<RoomQueryPanacheModel> getFreeRoomsByDateAndCapacity(LocalDate startDate, LocalDate endDate, int capacity) {
        return RoomQueryPanacheModel.find(
            "isAvailable = true AND maxCapacity >= ?1 AND NOT EXISTS " +
            "(SELECT a FROM RoomAvailabilityModel a WHERE a.roomId = roomId " +
            "AND ((a.startDate <= ?2 AND a.endDate >= ?3)))",
            capacity, endDate, startDate
        ).list();
    }

    @Transactional
    public void deleteAll() {
        RoomQueryPanacheModel.deleteAll();
    }
}