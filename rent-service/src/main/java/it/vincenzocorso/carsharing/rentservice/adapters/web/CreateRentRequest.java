package it.vincenzocorso.carsharing.rentservice.adapters.web;

import jakarta.validation.constraints.NotNull;

public record CreateRentRequest(
	@NotNull(message = "The customer id must be not null")
	String customerId,
	@NotNull(message = "The vehicle id must be not null")
	String vehicleId
) {}
