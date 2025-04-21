package at.fhv.sys.hotel.domain;

import java.time.LocalDate;

public class Booking {
    private String id;
    private String roomId;
    private String customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;
    private boolean isPaid;
    private boolean isCancelled;

    public Booking(String roomId, String customerId, LocalDate startDate, LocalDate endDate) {
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("Room ID is required");
        }
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID is required");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start date is required");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End date is required");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot book in the past");
        }

        this.id = java.util.UUID.randomUUID().toString();
        this.roomId = roomId.trim();
        this.customerId = customerId.trim();
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPaid = false;
        this.isCancelled = false;
        this.totalPrice = 0.0;
    }

    public String getId() {
        return id;
    }

    public String getRoomId() {
        return roomId;
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

    public void setTotalPrice(double totalPrice) {
        if (totalPrice < 0) {
            throw new IllegalArgumentException("Total price cannot be negative");
        }
        this.totalPrice = totalPrice;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancel() {
        if (isCancelled) {
            throw new IllegalStateException("Booking is already cancelled");
        }
        if (isPaid) {
            throw new IllegalStateException("Cannot cancel a paid booking");
        }
        isCancelled = true;
    }

    public void calculateTotalPrice(double pricePerNight) {
        if (pricePerNight <= 0) {
            throw new IllegalArgumentException("Price per night must be positive");
        }
        if (isCancelled) {
            throw new IllegalStateException("Cannot calculate price for cancelled booking");
        }
        long nights = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        this.totalPrice = pricePerNight * nights;
    }

    public void updateDates(LocalDate newStartDate, LocalDate newEndDate) {
        if (isCancelled) {
            throw new IllegalStateException("Cannot update dates for cancelled booking");
        }
        if (isPaid) {
            throw new IllegalStateException("Cannot update dates for paid booking");
        }
        if (newStartDate == null || newEndDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        if (newEndDate.isBefore(newStartDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        if (newStartDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot book in the past");
        }

        this.startDate = newStartDate;
        this.endDate = newEndDate;
    }
}