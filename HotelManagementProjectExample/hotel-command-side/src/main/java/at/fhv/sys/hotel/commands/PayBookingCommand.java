package at.fhv.sys.hotel.commands;

public record PayBookingCommand(String bookingId, String paymentMethod) {
} 