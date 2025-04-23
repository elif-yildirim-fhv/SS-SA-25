package at.fhv.sys.hotel.service;

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

    @Transactional
    public void deleteCustomer(String customerId) {
        CustomerQueryPanacheModel customer = CustomerQueryPanacheModel.findByCustomerId(customerId);
        if (customer != null) {
            customer.delete();
        }
    }

    public CustomerQueryPanacheModel getCustomerById(String customerId) {
        return CustomerQueryPanacheModel.findByCustomerId(customerId);
    }

    public List<CustomerQueryPanacheModel> searchCustomersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllCustomers();
        }
        return CustomerQueryPanacheModel.find("name LIKE ?1", "%" + name + "%").list();
    }
}