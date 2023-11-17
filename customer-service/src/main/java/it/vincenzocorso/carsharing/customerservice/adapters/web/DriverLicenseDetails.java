package it.vincenzocorso.carsharing.customerservice.adapters.web;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;

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
