package at.fhv.sys.hotel.commands;

import org.wildfly.common.annotation.NotNull;

import java.time.LocalDate;
public record CreateCustomerCommand(
		@NotNull String customerId,
		String name,
		String email,
		String address,
		LocalDate birthDate
) {
}