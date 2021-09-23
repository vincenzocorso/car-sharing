package it.vincenzocorso.carsharing.customerservice.api.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class DriverLicenseDetails {
	@NotEmpty(message = "The license number must not be null nor empty")
	public final String licenseNumber;

	@NotNull(message = "The license issue date must not be null")
	@Past(message = "The license issue date must be in the past")
	public final LocalDate issueDate;

	@NotNull(message = "The license expiry date must not be null")
	@Future(message = "The license expiry date must be in the future")
	public final LocalDate expiryDate;
}
