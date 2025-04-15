package at.fhv.sys.hotel.domain;

import java.time.LocalDate;

public class Booking {
    private String id;
    private String roomId;
    private String userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;
    private boolean isPaid;

    public Booking(String roomId, String userId, LocalDate startDate, LocalDate endDate) {
        this.id = java.util.UUID.randomUUID().toString();
        this.roomId = roomId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPaid = false;
        this.totalPrice = 0.0;
    }

    public String getId() {
        return id;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getUserId() {
        return userId;
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

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void calculateTotalPrice(double roomPrice) {
        this.totalPrice = roomPrice;
    }
} 