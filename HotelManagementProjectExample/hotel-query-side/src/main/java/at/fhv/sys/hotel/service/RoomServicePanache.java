package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.PaymentQueryPanacheModel;
import at.fhv.sys.hotel.models.RoomQueryPanacheModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class RoomServicePanache {

    @Inject
    private EntityManager em;

    @Transactional
    public void createRoom(RoomQueryPanacheModel room) {
        room.persist();
    }

    @Transactional
    public void deleteRoom(RoomQueryPanacheModel room) {
        em.remove(room);
    }

    @Transactional
    public void updateRoom(RoomQueryPanacheModel room) {
        RoomQueryPanacheModel existingRoom = RoomQueryPanacheModel.findByRoomId(room.roomId);
        if (room != null) {
            existingRoom.roomType = room.roomType;
            existingRoom.roomNumber = room.roomNumber;
            existingRoom.maxCapacity = room.maxCapacity;
            existingRoom.isAvailable = room.isAvailable;
            existingRoom.price = room.price;
            em.merge(existingRoom);
        }
    }

    public List<RoomQueryPanacheModel> getAllPayments() {
        return RoomQueryPanacheModel.listAll();
    }

}
