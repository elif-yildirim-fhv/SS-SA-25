package at.fhv.sys.hotel.commands;

import java.time.LocalDate;

public record BookRoomCommand(String roomId, String customerId, LocalDate startDate, LocalDate endDate) {
} 