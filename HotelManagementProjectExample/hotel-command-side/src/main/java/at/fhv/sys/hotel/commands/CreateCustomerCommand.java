package at.fhv.sys.hotel.commands;

import java.time.LocalDate;

public record CreateCustomerCommand(String customerId, String name, String email, String address, LocalDate birthDate) {
}
