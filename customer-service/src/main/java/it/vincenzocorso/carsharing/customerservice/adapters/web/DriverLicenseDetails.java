package it.vincenzocorso.carsharing.customerservice.adapters.web;

import lombok.Builder;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Builder
public record DriverLicenseDetails (
	@NotEmpty(message = "The license number must not be null nor empty")
	String licenseNumber,

	@NotNull(message = "The license issue date must not be null")
	@Past(message = "The license issue date must be in the past")
	LocalDate issueDate,

	@NotNull(message = "The license expiry date must not be null")
	@Future(message = "The license expiry date must be in the future")
	LocalDate expiryDate
) {}
