package at.fhv.sys.hotel.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class BookingQueryModel extends PanacheEntity {
    public String bookingId;
    public String roomId;
    public String customerId;
    public LocalDate startDate;
    public LocalDate endDate;
    public double totalPrice;
    public boolean isPaid;
    public boolean isCancelled;
}
