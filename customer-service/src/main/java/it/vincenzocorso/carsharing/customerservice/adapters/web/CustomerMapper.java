package it.vincenzocorso.carsharing.customerservice.adapters.web;

import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import it.vincenzocorso.carsharing.customerservice.domain.models.CustomerDetails;
import it.vincenzocorso.carsharing.customerservice.domain.models.DriverLicense;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerMapper {
	public CustomerResponse convertToDto(Customer customer) {
		DriverLicenseDetails driverLicense = new DriverLicenseDetails(
			customer.getCustomerDetails().getDriverLicense().getLicenseNumber(),
			customer.getCustomerDetails().getDriverLicense().getIssueDate(),
			customer.getCustomerDetails().getDriverLicense().getExpiryDate()
		);
		return CustomerResponse.builder()
				.customerId(customer.getId())
				.firstName(customer.getCustomerDetails().getFirstName())
				.lastName(customer.getCustomerDetails().getLastName())
				.dateOfBirth(customer.getCustomerDetails().getDateOfBirth())
				.fiscalCode(customer.getCustomerDetails().getFiscalCode())
				.email(customer.getCustomerDetails().getEmail())
				.phoneNumber(customer.getCustomerDetails().getPhoneNumber())
				.driverLicense(driverLicense)
				.build();
	}

	public CustomerDetails convertFromDto(RegisterCustomerRequest customerRequest) {
		DriverLicense driverLicense = null;
		if(customerRequest.driverLicense() != null) {
			driverLicense = new DriverLicense(
				customerRequest.driverLicense().licenseNumber(),
				customerRequest.driverLicense().issueDate(),
				customerRequest.driverLicense().expiryDate()
			);
		}
		return CustomerDetails.builder()
				.firstName(customerRequest.firstName())
				.lastName(customerRequest.lastName())
				.dateOfBirth(customerRequest.dateOfBirth())
				.fiscalCode(customerRequest.fiscalCode())
				.email(customerRequest.email())
				.phoneNumber(customerRequest.phoneNumber())
				.driverLicense(driverLicense)
				.build();
	}
}
