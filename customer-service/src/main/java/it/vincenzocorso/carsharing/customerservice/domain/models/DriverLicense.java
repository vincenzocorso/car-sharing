package it.vincenzocorso.carsharing.customerservice.domain.models;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class DriverLicense {
	private final String licenseNumber;
	private final LocalDate issueDate;
	private final LocalDate expiryDate;

	boolean isExpired() {
		return this.expiryDate.isBefore(LocalDate.now());
	}
}
