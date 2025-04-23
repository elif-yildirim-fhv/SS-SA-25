package readside.projection;

import eventside.event.BookingCanceledEvent;
import eventside.event.BookingCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import readside.dto.BookedStay;
import readside.dto.FreeRoom;
import readside.infrastructure.BookingRepositoryInterface;
import readside.infrastructure.FreeRoomRepositoryInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Projection implements IProjection {
    private static final Logger logger = LoggerFactory.getLogger(Projection.class);

    @Autowired
    FreeRoomRepositoryInterface freeRoomRepository;

    @Autowired
    BookingRepositoryInterface bookingRepository;

    public void processIncomingBookingCreatedEvent(BookingCreatedEvent event) {
        try {
            if (event.getArrivalDate().isAfter(event.getDepartureDate())) {
                logger.error("Invalid booking dates: arrival {} is after departure {}", 
                    event.getArrivalDate(), event.getDepartureDate());
                return;
            }

            logger.info("Processing booking created event for booking ID: {}", event.getBookingId());
            
            List<FreeRoom> freeRooms = freeRoomRepository.getBetween(event.getArrivalDate(), event.getDepartureDate());
            List<FreeRoom> adaptedRooms = new ArrayList<>();

            // ... existing code ...

            logger.info("Successfully processed booking created event for booking ID: {}", event.getBookingId());
        } catch (Exception e) {
            logger.error("Error processing booking created event: {}", e.getMessage(), e);
            throw new ProjectionException("Failed to process booking created event", e);
        }
    }

    public void processIncomingBookingCanceledEvent(BookingCanceledEvent event) {
        try {
            logger.info("Processing booking canceled event for booking ID: {}", event.getBookingId());

            if (!bookingRepository.bookingById(event.getBookingId()).isPresent()) {
                logger.warn("Attempted to cancel non-existent booking: {}", event.getBookingId());
                return;
            }

            // ... existing code ...

            logger.info("Successfully processed booking canceled event for booking ID: {}", event.getBookingId());
        } catch (Exception e) {
            logger.error("Error processing booking canceled event: {}", e.getMessage(), e);
            throw new ProjectionException("Failed to process booking canceled event", e);
        }
    }
}

class ProjectionException extends RuntimeException {
    public ProjectionException(String message, Throwable cause) {
        super(message, cause);
    }
} 