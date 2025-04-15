package at.fhv.sys.hotel.commands;

import java.time.LocalDate;

public record BookRoomCommand(String roomId, String userId, LocalDate startDate, LocalDate endDate, Double price) {
} 