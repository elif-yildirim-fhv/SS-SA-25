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

    public List<CustomerQueryPanacheModel> searchCustomersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllCustomers();
        }
        return CustomerQueryPanacheModel.find("name LIKE ?1", "%" + name + "%").list();
    }
}