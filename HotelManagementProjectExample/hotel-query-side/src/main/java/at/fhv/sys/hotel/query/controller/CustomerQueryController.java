package at.fhv.sys.hotel.query.controller;

import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.commands.shared.events.CustomerUpdated;
import at.fhv.sys.hotel.models.CustomerQueryPanacheModel;
import at.fhv.sys.hotel.projection.CustomerProjection;
import at.fhv.sys.hotel.service.CustomerServicePanache;
import at.fhv.sys.hotel.DTO.GetCustomerDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logmanager.Logger;

import java.util.List;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerQueryController {

    @Inject
    CustomerProjection customerProjection;

    @Inject
    CustomerServicePanache customerService;

    public CustomerQueryController() {
    }

    @POST
    @Path("/customerCreated")
    public Response customerCreated(CustomerCreated event) {
        Logger.getAnonymousLogger().info("Received event: " + event);
        customerProjection.processCustomerCreatedEvent(event);
        return Response.ok(event).build();
    }

    @GET
    @Path("/customers")
    public Response getAllCustomers() {
        List<CustomerQueryPanacheModel> customers = customerService.getAllCustomers();
        return Response.ok(customers).build();
    }

    @GET
    @Path("/customers/search")
    public Response searchCustomersByName(@QueryParam("name") String name) {
        List<GetCustomerDTO> customers = customerProjection.getCustomers(name);
        return Response.ok(customers).build();
    }
}