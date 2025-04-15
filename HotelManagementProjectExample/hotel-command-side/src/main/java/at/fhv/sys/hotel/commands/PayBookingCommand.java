package at.fhv.sys.hotel.commands;

import java.time.LocalDate;

public record PayBookingCommand(String bookingId, String paymentMethod, LocalDate paymentDate) {
} 