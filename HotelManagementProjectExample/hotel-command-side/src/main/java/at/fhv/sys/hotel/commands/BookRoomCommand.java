package at.fhv.sys.hotel.commands;

import java.time.LocalDate;

public record BookRoomCommand(String id, String roomId, String customerId, LocalDate startDate, LocalDate endDate, double totalPrice, boolean isPaid, boolean isCancelled) {
} 