package it.vincenzocorso.carsharing.customerservice.adapters.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CustomerRestApi {
	@GET
	@Path("{customerId}")
	Response getCustomer(@PathParam("customerId") String customerId);

	@GET
	Response getCustomers(
			@QueryParam("limit") @DefaultValue("10")
				@Min(value = 1, message = "The limit must be positive")
				@Max(value = 200, message = "The limit must be at most 200") Integer limit,
			@QueryParam("offset") @DefaultValue("0")
				@Min(value = 0, message = "Offset must be greater or equal to zero") Integer offset);

	@POST
	Response registerCustomer(@Valid RegisterCustomerRequest request);
}
