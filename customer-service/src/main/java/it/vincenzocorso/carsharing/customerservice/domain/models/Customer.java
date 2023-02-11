package it.vincenzocorso.carsharing.customerservice.domain.models;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;
import it.vincenzocorso.carsharing.common.messaging.events.ResultWithEvents;
import it.vincenzocorso.carsharing.customerservice.domain.events.CustomerCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * This class represents a Customer aggregate.
 * The customer is identified by an id.
 * In order to rent a vehicle, the customer's driver license and email must have been verified
 */
@AllArgsConstructor
@Getter
public class Customer {
	private String id;
	private final CustomerDetails customerDetails;
	private boolean driverLicenseVerified;
	private boolean emailVerified;

	private Customer(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
		this.driverLicenseVerified = false;
		this.emailVerified = false;
	}

	public static ResultWithEvents<Customer> create(CustomerDetails customerDetails) {
		Customer customer = new Customer(customerDetails);
		DomainEvent domainEvent = CustomerCreatedEvent.builder()
				.firstName(customer.customerDetails.getFirstName())
				.lastName(customer.customerDetails.getLastName())
				.dateOfBirth(customer.customerDetails.getDateOfBirth().toString())
				.fiscalCode(customer.customerDetails.getFiscalCode())
				.email(customer.customerDetails.getEmail())
				.phoneNumber(customer.customerDetails.getPhoneNumber())
				.build();
		return ResultWithEvents.of(customer, List.of(domainEvent));
	}

	public void verifyEmail() {
		this.emailVerified = true;
	}

	public void verifyDriverLicense() {
		this.driverLicenseVerified = true;
	}

	public boolean canRentAVehicle() {
		if(!this.emailVerified || !this.driverLicenseVerified)
			return false;

		return !this.getCustomerDetails().getDriverLicense().isExpired();
	}

	public void setId(String id) {
		this.id = id;
	}
}
