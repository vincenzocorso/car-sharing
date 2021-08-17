package it.vincenzocorso.carsharing.rentservice.api.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class CreateVehicleRequest {
	@NotNull(message = "The vehicle id must be not null")
	public final String vehicleId;
}
