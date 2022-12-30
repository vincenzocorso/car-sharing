package it.vincenzocorso.carsharing.customerservice.adapters.web;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(CustomerRestApi.PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CustomerRestApi {
	String PATH = "/customers";

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
