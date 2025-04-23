package at.fhv.sys.hotel.projection;

import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.commands.shared.events.CustomerUpdated;
import at.fhv.sys.hotel.models.CustomerQueryModel;
import at.fhv.sys.hotel.service.CustomerService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logmanager.Logger;

import java.util.List;

@ApplicationScoped
public class CustomerProjection implements Projection {

    @Inject
    CustomerService customerService;

    private static final Logger logger = Logger.getLogger(CustomerProjection.class.getName());

    public CustomerProjection() {
    }

    @Override
    @Transactional
    public void processEvent(Object event) {
        if (event instanceof CustomerCreated) {
            processCustomerCreatedEvent((CustomerCreated) event);
        } else if (event instanceof CustomerUpdated) {
            processCustomerUpdateEvent((CustomerUpdated) event);
        }
    }

    @Override
    public void clearState() {
        try {
            customerService.deleteAll();
        } catch (Exception e) {
            logger.severe("Error clearing customer state: " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void processCustomerCreatedEvent(CustomerCreated event) {
        try {
            logger.info("Processing CustomerCreated event: " + event);

            CustomerQueryModel customer = new CustomerQueryModel(
                event.getCustomerId(),
                event.getName(),
                event.getEmail(),
                event.getAddress(),
                event.getBirthDate()
            );
            customerService.createCustomer(customer);

            logger.info("Successfully processed CustomerCreated event for customer: " + event.getCustomerId());
        } catch (Exception e) {
            logger.severe("Error processing CustomerCreated event: " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void processCustomerUpdateEvent(CustomerUpdated event) {
        try {
            logger.info("Processing CustomerUpdated event: " + event);

            CustomerQueryModel customer = new CustomerQueryModel(
                event.getCustomerId(),
                event.getName(),
                event.getEmail(),
                event.getAddress(),
                event.getBirthDate()
            );
            customerService.updateCustomer(customer);

            logger.info("Successfully processed CustomerUpdated event for customer: " + event.getCustomerId());
        } catch (Exception e) {
            logger.severe("Error processing CustomerUpdated event: " + e.getMessage());
            throw e;
        }
    }

    public List<CustomerQueryModel> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public CustomerQueryModel getCustomerById(String customerId) {
        return customerService.getCustomerById(customerId);
    }
}
