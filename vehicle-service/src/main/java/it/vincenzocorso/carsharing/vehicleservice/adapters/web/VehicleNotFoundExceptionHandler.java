package it.vincenzocorso.carsharing.vehicleservice.adapters.web;

import it.vincenzocorso.carsharing.common.web.ErrorResponse;
import it.vincenzocorso.carsharing.common.web.ErrorResponses;
import it.vincenzocorso.carsharing.common.web.Issue;
import it.vincenzocorso.carsharing.vehicleservice.domain.exceptions.VehicleNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class VehicleNotFoundExceptionHandler implements ExceptionMapper<VehicleNotFoundException> {
	@Override
	public Response toResponse(VehicleNotFoundException ex) {
		List<Issue> issues = List.of(new Issue("vehicleId", "The vehicle with id " + ex.getVehicleId() + " was not found"));
		ErrorResponse payload = ErrorResponses.makeResourceNotFoundErrorResponse(issues);
		return Response.status(Response.Status.NOT_FOUND).entity(payload).build();
	}
}
