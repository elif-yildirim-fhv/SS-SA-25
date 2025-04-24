package at.fhv.sys.hotel.projection.customer;

import at.fhv.sys.hotel.service.CustomerServicePanache;
import at.fhv.sys.hotel.DTO.GetCustomerDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CustomerProjection {
    @Inject
    CustomerServicePanache customerService;

    public List<GetCustomerDTO> getAllOrSearchByName(String name) {
        // aus HotelProjection.getCustomers…
        return /* … */;
    }
} 