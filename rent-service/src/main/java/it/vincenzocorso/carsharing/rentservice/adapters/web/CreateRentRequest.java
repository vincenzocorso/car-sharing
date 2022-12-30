package it.vincenzocorso.carsharing.rentservice.adapters.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class CreateRentRequest {
	@NotNull(message = "The customer id must be not null")
	public final String customerId;

	@NotNull(message = "The vehicle id must be not null")
	public final String vehicleId;
}
