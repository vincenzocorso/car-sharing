package it.vincenzocorso.carsharing.customerservice.adapters.persistence.mongodb;

import java.time.LocalDate;

public class DriverLicenseDocument {
	public String licenseNumber;
	public LocalDate issueDate;
	public LocalDate expiryDate;
}
