package at.fhv.sys.hotel.query.controller.customer;

import at.fhv.sys.hotel.projection.customer.CustomerProjection;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logmanager.Logger;
import java.util.List;

@Path("/api/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerQueryController {
    private static final Logger LOGGER = Logger.getLogger(CustomerQueryController.class.getName());

    @Inject
    CustomerProjection customerProjection;

    @GET
    public Response getCustomers(@QueryParam("name") String name) {
        List<?> customers = customerProjection.getAllOrSearchByName(name);
        return Response.ok(customers).build();
    }
} 