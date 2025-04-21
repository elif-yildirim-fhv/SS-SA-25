package at.fhv.sys.hotel.projection;

import at.fhv.sys.hotel.commands.shared.events.RoomCreated;
import at.fhv.sys.hotel.models.RoomQueryModel;
import at.fhv.sys.hotel.service.RoomService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
public class RoomProjection {

    private static final Logger LOGGER = Logger.getLogger(RoomProjection.class.getName());

    @Inject
    RoomService roomService;

    public void processRoomCreatedEvent(RoomCreated event) {
        try {
            LOGGER.info("Processing RoomCreated event: " + event);
            
            RoomQueryModel room = new RoomQueryModel(
                event.getRoomId(),
                event.getRoomNumber(),
                event.getPrice(),
                event.getMaxCapacity(),
                event.isAvailable(),
                event.getRoomType()
            );
            
            roomService.createRoom(room);
            LOGGER.info("Room created successfully in query model: " + event.getRoomId());
        } catch (Exception e) {
            LOGGER.severe("Error processing RoomCreated event: " + e.getMessage());
            throw e;
        }
    }
} 