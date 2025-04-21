package at.fhv.sys.hotel.projection;

import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.commands.shared.events.CustomerUpdated;
import at.fhv.sys.hotel.models.CustomerQueryModel;
import at.fhv.sys.hotel.models.CustomerQueryPanacheModel;
import at.fhv.sys.hotel.service.CustomerService;
import at.fhv.sys.hotel.service.CustomerServicePanache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import java.util.logging.Logger;

@ApplicationScoped
public class CustomerProjection {

    @Inject
    CustomerService customerService;

    @Inject
    CustomerServicePanache customerServicePanache;

    public void processCustomerCreatedEvent(CustomerCreated customerCreatedEvent) {
        Logger.getAnonymousLogger().info("Processing event: " + customerCreatedEvent);
        customerService.createCustomer(new CustomerQueryModel(customerCreatedEvent.getCustomerId(), customerCreatedEvent.getName(), customerCreatedEvent.getEmail(), customerCreatedEvent.getAddress(), customerCreatedEvent.getBirthDate()));

        CustomerQueryPanacheModel customer = new CustomerQueryPanacheModel();
        customer.customerId = customerCreatedEvent.getCustomerId();
        customer.name = customerCreatedEvent.getName();
        customer.email = customerCreatedEvent.getEmail();
        customer.address = customerCreatedEvent.getAddress();
        customer.birthDate = customerCreatedEvent.getBirthDate();
        customerServicePanache.createCustomer(customer);

    }

    public void processCustomerUpdateEvent(CustomerUpdated event) {
        Logger.getAnonymousLogger().info("Processing CustomerUpdated event: " + event);
        CustomerQueryModel customerModel = new CustomerQueryModel(
                    event.getCustomerId(),
                    event.getName(),
                    event.getEmail(),
                    event.getAddress(),
                    event.getBirthDate());

            customerService.updateCustomer(customerModel);

            CustomerQueryPanacheModel customerPanache = new CustomerQueryPanacheModel();
            customerPanache.customerId = event.getCustomerId();
            customerPanache.name = event.getName();
            customerPanache.email = event.getEmail();
            customerPanache.address = event.getAddress();
            customerPanache.birthDate = event.getBirthDate();
            customerServicePanache.updateCustomer(customerPanache);

    }


}
