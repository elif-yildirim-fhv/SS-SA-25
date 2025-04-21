package at.fhv.sys.hotel.commands;

import java.time.LocalDate;

public record UpdateCustomerCommand(String customerId, String name, String email, String address, LocalDate birtDate) {
} 