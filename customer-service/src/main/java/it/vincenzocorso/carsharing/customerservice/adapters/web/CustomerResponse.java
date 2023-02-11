package it.vincenzocorso.carsharing.customerservice.adapters.web;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CustomerResponse(
	String customerId,
	String firstName,
	String lastName,
	LocalDate dateOfBirth,
	String fiscalCode,
	String email,
	String phoneNumber,
	DriverLicenseDetails driverLicense
) {}
