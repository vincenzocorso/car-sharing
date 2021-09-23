package it.vincenzocorso.carsharing.customerservice.api.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@Builder
@Jacksonized
public class RegisterCustomerRequest {
	@NotEmpty(message = "The customer first name must not be null nor empty")
	public final String firstName;

	@NotEmpty(message = "The customer last name must not be null nor empty")
	public final String lastName;

	@NotNull(message = "The date of birth of the customer must not be null")
	@Past(message = "The date of birth of the customer must be in the past")
	public final LocalDate dateOfBirth;

	@NotEmpty(message = "The customer fiscal code must not be null nor empty")
	@Size(min = 16, max = 16, message = "The customer fiscal code must be 16 characters long")
	public final String fiscalCode;

	@NotEmpty(message = "The customer email must not be null nor empty")
	@Email(message = "The customer email must be valid")
	public final String email;

	@NotEmpty(message = "The customer fiscal code must not be null nor empty")
	public final String phoneNumber;

	public final DriverLicenseDetails driverLicense;
}
