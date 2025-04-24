package at.fhv.sys.hotel.commands;

import at.fhv.sys.hotel.client.EventBusClient;
import at.fhv.sys.hotel.commands.shared.events.*;
import at.fhv.sys.hotel.domain.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ApplicationScoped
public class CustomerAggregate {
	private final Map<String, Customer> customers = new HashMap<>();

	@Inject
	@RestClient
	EventBusClient eventClient;

	private static final Logger LOGGER = Logger.getLogger(CustomerAggregate.class.getName());

	public String handle(CreateCustomerCommand command) {
		try {

			Customer customer = new Customer(
					command.name(),
					command.email(),
					command.address(),
					command.birthDate()
			);

			// Speichere Customer im Aggregate
			customers.put(customer.getCustomerId(), customer);

			// Erstelle Event mit allen notwendigen Daten
			CustomerCreated event = new CustomerCreated(
					customer.getCustomerId(),
					customer.getName(),
					customer.getEmail(),
					customer.getAddress(),
					customer.getBirthDate()
			);

			// Sende Event
			eventClient.processCustomerCreatedEvent(event);
			LOGGER.info("Customer created successfully with ID: " + customer.getCustomerId());

			return customer.getCustomerId();
		} catch (IllegalArgumentException e) {
			LOGGER.severe("Failed to create customer: " + e.getMessage());
			throw e;
		}
	}

	public void handle(UpdateCustomerCommand command) {
		Customer customer = customers.get(command.customerId());
		if (customer == null) {
			throw new IllegalArgumentException("Customer not found");
		}

		try {
			customer.update(
					command.name(),
					command.email(),
					command.address(),
					command.birtDate()
			);

			CustomerUpdated event = new CustomerUpdated(
					customer.getCustomerId(),
					customer.getName(),
					customer.getEmail(),
					customer.getAddress(),
					customer.getBirthDate()
			);

			eventClient.processCustomerUpdatedEvent(event);
			LOGGER.info("Customer updated successfully: " + customer.getCustomerId());
		} catch (IllegalArgumentException e) {
			LOGGER.severe("Failed to update customer: " + e.getMessage());
			throw e;
		}
	}


	public Customer getCustomer(String customerId) {
		return customers.get(customerId);
	}
}
