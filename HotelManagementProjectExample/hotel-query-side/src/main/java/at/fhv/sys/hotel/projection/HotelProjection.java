package at.fhv.sys.hotel.projection;

import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.BookingCancelled;
import at.fhv.sys.hotel.models.RoomAvailabilityModel;
import at.fhv.sys.hotel.models.BookingQueryModel;
import at.fhv.sys.hotel.models.RoomQueryPanacheModel;
import at.fhv.sys.hotel.models.CustomerQueryPanacheModel;
import at.fhv.sys.hotel.service.RoomAvailabilityService;
import at.fhv.sys.hotel.service.BookingService;
import at.fhv.sys.hotel.service.RoomService;
import at.fhv.sys.hotel.service.CustomerServicePanache;
import at.fhv.sys.hotel.DTO.FreeRoomsDTO;
import at.fhv.sys.hotel.DTO.GetBookingsDTO;
import at.fhv.sys.hotel.DTO.GetCustomerDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logmanager.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class HotelProjection implements Projection {
    private static final Logger LOGGER = Logger.getLogger(HotelProjection.class.getName());

    @Inject
    RoomAvailabilityService roomAvailabilityService;

    @Inject
    BookingService bookingService;

    @Inject
    RoomService roomService;
    
    @Inject
    CustomerServicePanache customerService;

    public HotelProjection() {
    }

    @Override
    @Transactional
    public void processEvent(Object event) {
        if (event instanceof BookingCreated) {
            processIncomingBookingCreatedEvent((BookingCreated) event);
        } else if (event instanceof BookingCancelled) {
            processIncomingBookingCanceledEvent((BookingCancelled) event);
        }
    }
    
    @Override
    public void clearState() {
        try {
            bookingService.deleteAll();
            roomAvailabilityService.deleteAll();
        } catch (Exception e) {
            LOGGER.severe("Error clearing hotel state: " + e.getMessage());
            throw e;
        }
    }

    // GetBookings mit Zeitraumparameter
    public List<GetBookingsDTO> getBookingsByTimeRange(LocalDate startDate, LocalDate endDate) {
        List<BookingQueryModel> bookings = bookingService.findByDateRange(startDate, endDate);
        return convertToBookingsDTO(bookings);
    }
    
    private List<GetBookingsDTO> convertToBookingsDTO(List<BookingQueryModel> bookings) {
        return bookings.stream()
            .map(booking -> {
                Set<String> rooms = new HashSet<>();
                rooms.add(booking.getRoomId());
                
                return new GetBookingsDTO(
                    booking.getBookingId(),
                    rooms,
                    booking.getCustomerId(),
                    booking.getStartDate(),
                    booking.getEndDate(),
                    booking.getTotalPrice(),
                    booking.isPaid(),
                    booking.isCancelled()
                );
            })
            .collect(Collectors.toList());
    }

    // GetFreeRooms mit Zeitraum und Personenanzahl
    public List<FreeRoomsDTO> getFreeRooms(LocalDate startDate, LocalDate endDate, int numberOfPersons) {
        List<RoomQueryPanacheModel> freeRooms = roomService.getFreeRoomsByDateAndCapacity(startDate, endDate, numberOfPersons);
        return convertToFreeRoomsDTO(freeRooms);
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

    // GetCustomers mit optionalem Namen
    public List<GetCustomerDTO> getCustomers(String name) {
        List<CustomerQueryPanacheModel> customers;
        
        if (name == null || name.trim().isEmpty()) {
            customers = customerService.getAllCustomers();
        } else {
            customers = customerService.searchCustomersByName(name);
        }
        
        return convertToCustomerDTO(customers);
    }
    
    private List<GetCustomerDTO> convertToCustomerDTO(List<CustomerQueryPanacheModel> customers) {
        return customers.stream()
            .map(customer -> new GetCustomerDTO(
                customer.customerId,
                customer.name,
                customer.email,
                customer.address,
                customer.birthDate
            ))
            .collect(Collectors.toList());
    }

    @Transactional
    public void processIncomingBookingCreatedEvent(BookingCreated event) {
        try {
            LOGGER.info("Processing BookingCreated event: " + event);
            
            // Freie Zimmer für den Zeitraum finden
            List<RoomAvailabilityModel> freeRooms = roomAvailabilityService.findAvailableRooms(
                event.getStartDate(), event.getEndDate());
            List<RoomAvailabilityModel> adaptedRooms = new ArrayList<>();
            
            // Filtern nach den gebuchten Zimmern und Verfügbarkeit anpassen
            freeRooms.stream()
                .filter(r -> r.getRoomId().equals(event.getRoomId()))
                .forEach(r -> {
                    // Verfügbarkeit vor der Buchung
                    if (r.getStartDate().isBefore(event.getStartDate())) {
                        adaptedRooms.add(new RoomAvailabilityModel(
                            r.getRoomId(),
                            r.getStartDate(),
                            event.getStartDate()
                        ));
                    }
                    
                    // Verfügbarkeit nach der Buchung
                    if (r.getEndDate().isAfter(event.getEndDate())) {
                        adaptedRooms.add(new RoomAvailabilityModel(
                            r.getRoomId(),
                            event.getEndDate(),
                            r.getEndDate()
                        ));
                    }
                    
                    // Verfügbarkeit entfernen
                    roomAvailabilityService.removeAvailability(r);
                });
            
            // Angepasste Verfügbarkeiten hinzufügen
            adaptedRooms.forEach(r -> roomAvailabilityService.addAvailability(r));
            
            // Buchung erstellen
            BookingQueryModel booking = new BookingQueryModel(
                event.getBookingId(),
                event.getCustomerId(),
                event.getRoomId(),
                event.getStartDate(),
                event.getEndDate(),
                event.getTotalPrice(),
                false
            );
            bookingService.createBooking(booking);
            
            LOGGER.info("Successfully processed BookingCreated event for booking: " + event.getBookingId());
        } catch (Exception e) {
            LOGGER.severe("Error processing BookingCreated event: " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void processIncomingBookingCanceledEvent(BookingCancelled event) {
        try {
            LOGGER.info("Processing BookingCancelled event: " + event);
            
            // Buchung suchen und stornieren
            BookingQueryModel booking = bookingService.findById(event.getBookingId());
            if (booking != null) {
                booking.setCancelled(true);
                bookingService.updateBooking(booking);
                
                // Randfälle für Verfügbarkeiten an Start- und Enddatum suchen
                List<RoomAvailabilityModel> edgeCases = Stream.concat(
                    roomAvailabilityService.findAdjacentAvailability(booking.getRoomId(), 
                        booking.getStartDate(), booking.getStartDate()).stream(),
                    roomAvailabilityService.findAdjacentAvailability(booking.getRoomId(), 
                        booking.getEndDate(), booking.getEndDate()).stream()
                ).collect(Collectors.toList());
                
                if (edgeCases.isEmpty()) {
                    // Keine angrenzenden Verfügbarkeiten, einfach neue erstellen
                    roomAvailabilityService.addAvailability(new RoomAvailabilityModel(
                        booking.getRoomId(),
                        booking.getStartDate(),
                        booking.getEndDate()
                    ));
                } else {
                    // Verfügbarkeiten zusammenführen
                    LocalDate startDate = edgeCases.stream()
                        .map(RoomAvailabilityModel::getStartDate)
                        .min(LocalDate::compareTo)
                        .orElse(booking.getStartDate());
                    
                    LocalDate endDate = edgeCases.stream()
                        .map(RoomAvailabilityModel::getEndDate)
                        .max(LocalDate::compareTo)
                        .orElse(booking.getEndDate());
                    
                    // Neue zusammengeführte Verfügbarkeit erstellen
                    roomAvailabilityService.addAvailability(new RoomAvailabilityModel(
                        booking.getRoomId(),
                        startDate.isBefore(booking.getStartDate()) ? startDate : booking.getStartDate(),
                        endDate.isAfter(booking.getEndDate()) ? endDate : booking.getEndDate()
                    ));
                    
                    // Alte Verfügbarkeiten entfernen
                    edgeCases.forEach(r -> roomAvailabilityService.removeAvailability(r));
                }
            }
            
            LOGGER.info("Successfully processed BookingCancelled event for booking: " + event.getBookingId());
        } catch (Exception e) {
            LOGGER.severe("Error processing BookingCancelled event: " + e.getMessage());
            throw e;
        }
    }
} 