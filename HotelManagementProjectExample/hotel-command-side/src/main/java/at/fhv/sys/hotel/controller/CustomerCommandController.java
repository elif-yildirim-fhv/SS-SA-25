package at.fhv.sys.hotel.controller;

import at.fhv.sys.hotel.commands.CreateCustomerCommand;
import at.fhv.sys.hotel.commands.CustomerAggregate;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.UUID;

@Path("/api/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerCommandController {

    @Inject
    CustomerAggregate customerAggregate;

    @POST
    @Path("/create")
    public Response createCustomer(
            @QueryParam("name") String name,
            @QueryParam("email") String email,
            @QueryParam("address") String address,
            @QueryParam("birthDate") String birthDate) {
        try {
            LocalDate parsedBirthDate = LocalDate.parse(birthDate);

            String customerId = UUID.randomUUID().toString();
            String createdId = customerAggregate.handle(new CreateCustomerCommand(
                    customerId,
                    name,
                    email,
                    address,
                    parsedBirthDate
            ));
            return Response.ok(createdId).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{customerId}")
    public Response updateCustomer(
            @PathParam("customerId") String customerId,
            @QueryParam("name") String name,
            @QueryParam("email") String email,
            @QueryParam("address") String address,
            @QueryParam("birthDate") String birthDate) {
        try {
            // TODO: Implement update command
            return Response.ok("Customer updated successfully").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
