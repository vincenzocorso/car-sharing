package it.vincenzocorso.carsharing.vehicleservice.adapters.web;

import it.vincenzocorso.carsharing.common.web.ErrorResponse;
import it.vincenzocorso.carsharing.common.web.ErrorResponses;
import it.vincenzocorso.carsharing.common.web.Issue;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Iterator;
import java.util.List;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {
	@Override
	public Response toResponse(ConstraintViolationException ex) {
		List<Issue> issues = ex.getConstraintViolations().stream()
				.map(this::convertToIssue)
				.toList();

		ErrorResponse payload = ErrorResponses.makeValidationErrorResponse(issues);
		return Response.status(Response.Status.BAD_REQUEST).entity(payload).build();
	}

	private Issue convertToIssue(ConstraintViolation<?> constraintViolation) {
		Iterator<Path.Node> iterator = constraintViolation.getPropertyPath().iterator();
		Path.Node lastNode = iterator.next();
		while(iterator.hasNext())
			lastNode = iterator.next();

		return new Issue(lastNode.getName(), constraintViolation.getMessage());
	}
}
