package it.vincenzocorso.carsharing.customerservice.domain.models;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class CustomerDetails {
	private final String firstName;
	private final String lastName;
	private final LocalDate dateOfBirth;
	private final String fiscalCode;
	private String email;
	private String phoneNumber;
	private DriverLicense driverLicense;
}
