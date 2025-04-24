package at.fhv.sys.hotel.projection.room;

import at.fhv.sys.hotel.service.RoomService;
import at.fhv.sys.hotel.DTO.FreeRoomsDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class RoomProjection {
    @Inject
    RoomService roomService;

    public List<FreeRoomsDTO> getFree(LocalDate start, LocalDate end, int persons) {
        // aus HotelProjection.getFreeRooms…
        return /* … */;
    }
} 