package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.BookingQueryPanacheModel;
import at.fhv.sys.hotel.models.CustomerQueryPanacheModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CustomerServicePanache {

    public List<CustomerQueryPanacheModel> getAllCustomers() {
        return CustomerQueryPanacheModel.listAll();
    }

    @Transactional
    public void createCustomer(CustomerQueryPanacheModel customer) {
        customer.persist();
    }

    @Transactional
    public void updateCustomer(CustomerQueryPanacheModel customer) {
        CustomerQueryPanacheModel existingCustomer = CustomerQueryPanacheModel.findByCustomerId(customer.customerId);
        if (existingCustomer != null) {
            existingCustomer.name = customer.name;
            existingCustomer.email = customer.email;
            existingCustomer.address = customer.address;
            existingCustomer.birthDate = customer.birthDate;
            existingCustomer.persist();
        }
    }
}