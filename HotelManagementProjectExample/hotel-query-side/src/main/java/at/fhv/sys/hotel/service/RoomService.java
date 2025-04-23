package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.BookingQueryPanacheModel;
import at.fhv.sys.hotel.models.RoomQueryModel;
import at.fhv.sys.hotel.models.RoomQueryPanacheModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RoomService {
    private static final Logger LOG = Logger.getLogger(RoomService.class);

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    BookingServicePanache bookingService;

    public List<RoomQueryModel> getAllRooms() {
        return entityManager.createQuery("SELECT r FROM RoomQueryModel r", RoomQueryModel.class)
                .getResultList();
    }

    @Transactional
    public void createRoom(RoomQueryModel room) {
        LOG.info("Creating room with ID: " + room.getRoomId());
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
        return entityManager.find(RoomQueryModel.class, roomId);
    }

    public List<RoomQueryModel> getAvailableRooms() {
        return entityManager.createQuery("SELECT r FROM RoomQueryModel r WHERE r.isAvailable = true", RoomQueryModel.class)
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

    @Transactional
    public void updateRoomAvailability(String roomId, boolean isAvailable) {
        RoomQueryModel room = getRoomById(roomId);
        if (room != null) {
            room.setAvailable(isAvailable);
            entityManager.merge(room);
        }
    }

    public List<RoomQueryPanacheModel> getFreeRoomsByDateAndCapacity(LocalDate startDate, LocalDate endDate, int minCapacity) {
        // Get all rooms with sufficient capacity
        List<RoomQueryPanacheModel> suitableRooms = RoomQueryPanacheModel.find("maxCapacity >= ?1", minCapacity).list();

        // Get all bookings in the date range
        List<BookingQueryPanacheModel> bookingsInRange = bookingService.getBookingsByDateRange(startDate, endDate);

        // Extract room IDs that are booked in the date range
        List<String> bookedRoomIds = bookingsInRange.stream()
                .filter(booking -> !booking.isCancelled)
                .map(booking -> booking.roomId)
                .collect(Collectors.toList());

        // Filter out booked rooms
        return suitableRooms.stream()
                .filter(room -> !bookedRoomIds.contains(room.roomId))
                .collect(Collectors.toList());
    }
}