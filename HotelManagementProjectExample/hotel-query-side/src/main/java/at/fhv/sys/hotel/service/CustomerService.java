package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.CustomerQueryModel;
import at.fhv.sys.hotel.models.PaymentQueryPanacheModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class CustomerService {
    private static final Logger LOG = Logger.getLogger(CustomerService.class);

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    PaymentServicePanache paymentService;

    public List<CustomerQueryModel> getAllCustomers() {
        return entityManager.createQuery("SELECT c FROM CustomerQueryModel c", CustomerQueryModel.class)
                .getResultList();
    }

    @Transactional
    public void createCustomer(CustomerQueryModel customer) {
        LOG.info("Creating customer with ID: " + customer.getCustomerId());
        entityManager.persist(customer);
    }

    @Transactional
    public void updateCustomer(CustomerQueryModel customer) {
        CustomerQueryModel existingCustomer = entityManager.find(CustomerQueryModel.class, customer.getCustomerId());
        if (existingCustomer != null) {
            existingCustomer.setName(customer.getName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setAddress(customer.getAddress());
            existingCustomer.setBirthDate(customer.getBirthDate());
            entityManager.merge(existingCustomer);
        }
    }

    public CustomerQueryModel getCustomerById(String customerId) {
        return entityManager.find(CustomerQueryModel.class, customerId);
    }

    public List<PaymentQueryPanacheModel> getPaymentsByBookingId(String bookingId) {
        return paymentService.findByBookingId(bookingId);
    }

    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM CustomerQueryModel").executeUpdate();
    }
}
