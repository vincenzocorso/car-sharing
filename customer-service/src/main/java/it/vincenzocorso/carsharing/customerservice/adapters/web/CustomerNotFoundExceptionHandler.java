package it.vincenzocorso.carsharing.customerservice.adapters.web;

import it.vincenzocorso.carsharing.common.web.ErrorResponse;
import it.vincenzocorso.carsharing.common.web.ErrorResponses;
import it.vincenzocorso.carsharing.common.web.Issue;
import it.vincenzocorso.carsharing.customerservice.domain.exceptions.CustomerNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class CustomerNotFoundExceptionHandler implements ExceptionMapper<CustomerNotFoundException> {
	@Override
	public Response toResponse(CustomerNotFoundException ex) {
		List<Issue> issues = List.of(new Issue("customerId", "The customer with id " + ex.getCustomerId() + " was not found"));
		ErrorResponse payload = ErrorResponses.makeResourceNotFoundErrorResponse(issues);
		return Response.status(Response.Status.NOT_FOUND).entity(payload).build();
	}
}
