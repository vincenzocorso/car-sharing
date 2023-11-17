package it.vincenzocorso.carsharing.customerservice.adapters.web;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RegisterCustomerRequest(
	@NotEmpty(message = "The customer first name must not be null nor empty")
	String firstName,

	@NotEmpty(message = "The customer last name must not be null nor empty")
	String lastName,

	@NotNull(message = "The date of birth of the customer must not be null")
	@Past(message = "The date of birth of the customer must be in the past")
	LocalDate dateOfBirth,

	@NotEmpty(message = "The customer fiscal code must not be null nor empty")
	@Size(min = 16, max = 16, message = "The customer fiscal code must be 16 characters long")
	String fiscalCode,

	@NotEmpty(message = "The customer email must not be null nor empty")
	@Email(message = "The customer email must be valid")
	String email,

	@NotEmpty(message = "The customer fiscal code must not be null nor empty")
	String phoneNumber,

	DriverLicenseDetails driverLicense
) {}
