package at.fhv.sys.hotel.projection;

import at.fhv.sys.hotel.commands.shared.events.RoomCreated;
import at.fhv.sys.hotel.commands.shared.events.RoomUpdated;
import at.fhv.sys.hotel.models.RoomQueryPanacheModel;
import at.fhv.sys.hotel.models.RoomAvailabilityModel;
import at.fhv.sys.hotel.service.RoomService;
import at.fhv.sys.hotel.service.RoomAvailabilityService;
import at.fhv.sys.hotel.DTO.FreeRoomsDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logmanager.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RoomProjection implements Projection {
    private static final Logger LOGGER = Logger.getLogger(RoomProjection.class.getName());

    @Inject
    RoomService roomService;

    @Inject
    RoomAvailabilityService roomAvailabilityService;

    public RoomQueryPanacheModel getRoomById(String roomId) {
        return roomService.getRoomById(roomId);
    }

    public List<RoomQueryPanacheModel> getAllRooms() {
        return roomService.getAllRooms();
    }

    public List<FreeRoomsDTO> getAvailableRooms(LocalDate startDate, LocalDate endDate, int capacity) {
        List<RoomQueryPanacheModel> availableRooms = roomService.getFreeRoomsByDateAndCapacity(startDate, endDate, capacity);
        return convertToFreeRoomsDTO(availableRooms);
    }
    
    private List<FreeRoomsDTO> convertToFreeRoomsDTO(List<RoomQueryPanacheModel> rooms) {
        return rooms.stream()
            .map(room -> new FreeRoomsDTO(
                room.roomId,
                room.roomNumber,
                room.price,
                room.maxCapacity,
                room.isAvailable,
                room.roomType
            ))
            .collect(Collectors.toList());
    }

    public List<RoomQueryPanacheModel> getRoomsByType(String roomType) {
        return roomService.getRoomsByType(roomType);
    }

    public List<RoomQueryPanacheModel> getRoomsByCapacityRange(int minCapacity, int maxCapacity) {
        return roomService.getRoomsByCapacity(minCapacity);
    }

    @Override
    public void clearState() {
        try {
            roomService.deleteAll();
            roomAvailabilityService.deleteAll();
        } catch (Exception e) {
            LOGGER.severe("Error clearing room state: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void processEvent(Object event) {
        if (event instanceof RoomCreated) {
            processRoomCreatedEvent((RoomCreated) event);
        } else if (event instanceof RoomUpdated) {
            processRoomUpdatedEvent((RoomUpdated) event);
        }
    }

    @Transactional
    public void processRoomCreatedEvent(RoomCreated event) {
        try {
            LOGGER.info("Processing RoomCreated event: " + event);

            RoomQueryPanacheModel room = new RoomQueryPanacheModel(
                    event.getRoomId(),
                    event.getRoomNumber(),
                    event.getPrice(),
                    event.getMaxCapacity()
            );
            room.roomType = event.getRoomType();
            room.isAvailable = event.isAvailable();
            roomService.createRoom(room);

            if (event.isAvailable()) {
                LocalDate today = LocalDate.now();
                LocalDate endOfYear = today.plusYears(1);

                RoomAvailabilityModel availability = new RoomAvailabilityModel(
                        event.getRoomId(),
                        today,
                        endOfYear
                );
                roomAvailabilityService.addAvailability(availability);
            }

            LOGGER.info("Successfully processed RoomCreated event for room: " + event.getRoomId());
        } catch (Exception e) {
            LOGGER.severe("Error processing RoomCreated event: " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void processRoomUpdatedEvent(RoomUpdated event) {
        try {
            LOGGER.info("Processing RoomUpdated event: " + event);

            RoomQueryPanacheModel room = roomService.getRoomById(event.getRoomId());
            if (room != null) {
                room.roomNumber = event.getRoomNumber();
                room.price = event.getPrice();
                room.maxCapacity = event.getMaxCapacity();
                room.isAvailable = event.isAvailable();
                room.roomType = event.getRoomType();

                roomService.updateRoom(room);

                if (event.isAvailable() != room.isAvailable) {
                    if (event.isAvailable()) {
                        RoomAvailabilityModel availability = new RoomAvailabilityModel(
                            event.getRoomId(),
                            LocalDate.now(),
                            LocalDate.now().plusYears(1)
                        );
                        roomAvailabilityService.addAvailability(availability);
                    } else {
                        roomAvailabilityService.removeAvailabilityFromDate(
                            event.getRoomId(),
                            LocalDate.now()
                        );
                    }
                }
            }

            LOGGER.info("Successfully processed RoomUpdated event for room: " + event.getRoomId());
        } catch (Exception e) {
            LOGGER.severe("Error processing RoomUpdated event: " + e.getMessage());
            throw e;
        }
    }
} 