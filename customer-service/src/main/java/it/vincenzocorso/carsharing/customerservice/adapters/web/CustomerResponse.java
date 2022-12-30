package it.vincenzocorso.carsharing.customerservice.adapters.web;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Builder
@Jacksonized
public class CustomerResponse {
	public final String customerId;
	public final String firstName;
	public final String lastName;
	public final LocalDate dateOfBirth;
	public final String fiscalCode;
	public final String email;
	public final String phoneNumber;
	public final DriverLicenseDetails driverLicense;
}
