package at.fhv.sys.hotel.DTO;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class GetBookingsDTO {
    private String bookingId;
    private Set<String> rooms;
    private String customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;
    private boolean isPaid;
    private boolean isCancelled;

    public GetBookingsDTO(String bookingId, Set<String> rooms, String customerId, LocalDate startDate,
                          LocalDate endDate, double totalPrice, boolean isPaid, boolean isCancelled) {
        this.bookingId = bookingId;
        this.rooms = rooms;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.isPaid = isPaid;
        this.isCancelled = isCancelled;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Set<String> getRooms() {
        return Collections.unmodifiableSet(rooms);
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public boolean isCancelled() {
        return isCancelled;
    }
}
