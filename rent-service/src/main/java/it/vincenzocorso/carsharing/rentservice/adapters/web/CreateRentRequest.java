package it.vincenzocorso.carsharing.rentservice.adapters.web;

import jakarta.validation.constraints.NotEmpty;

public record CreateRentRequest(
	@NotEmpty(message = "The customer id must be present")
	String customerId,
	@NotEmpty(message = "The vehicle id must be present")
	String vehicleId
) {}
