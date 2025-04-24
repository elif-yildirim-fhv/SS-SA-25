package at.fhv.sys.hotel.projection.booking;

import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.BookingCancelled;
import at.fhv.sys.hotel.models.BookingQueryModel;
import at.fhv.sys.hotel.service.BookingService;
import at.fhv.sys.hotel.DTO.GetBookingsDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class BookingProjection {
    @Inject
    BookingService bookingService;

    @Transactional
    public void processCreated(BookingCreated event) { /* aus HotelProjection.processIncomingBookingCreatedEvent */ }

    @Transactional
    public void processCancelled(BookingCancelled event) { /* aus HotelProjection.processIncomingBookingCanceledEvent */ }

    public List<GetBookingsDTO> getByRange(LocalDate start, LocalDate end) {
        List<BookingQueryModel> models = bookingService.findByDateRange(start, end);
        // convert zu DTO…
        return /* … */;
    }
} 