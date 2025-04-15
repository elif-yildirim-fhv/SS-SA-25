package at.fhv.sys.hotel.commands;

public record UpdateCustomerCommand(String customerId, String name, String email, String address, String birthdate) {
} 