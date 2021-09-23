package it.vincenzocorso.carsharing.customerservice.adapters.persistence.mongodb;

import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import it.vincenzocorso.carsharing.customerservice.domain.models.CustomerDetails;
import it.vincenzocorso.carsharing.customerservice.domain.models.DriverLicense;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerDocumentMapper {
	public CustomerDocument convertToDocument(Customer customer) {
		CustomerDocument customerDocument = new CustomerDocument();
		customerDocument.id = customer.getId() != null ? new ObjectId(customer.getId()) : null;
		customerDocument.firstName = customer.getCustomerDetails().getFirstName();
		customerDocument.lastName = customer.getCustomerDetails().getLastName();
		customerDocument.dateOfBirth = customer.getCustomerDetails().getDateOfBirth();
		customerDocument.fiscalCode = customer.getCustomerDetails().getFiscalCode();
		customerDocument.email = customer.getCustomerDetails().getEmail();
		customerDocument.phoneNumber = customer.getCustomerDetails().getPhoneNumber();
		customerDocument.driverLicense = this.convertToDocument(customer.getCustomerDetails().getDriverLicense());
		customerDocument.driverLicenseVerified = customer.isDriverLicenseVerified();
		customerDocument.emailVerified = customer.isEmailVerified();
		return customerDocument;
	}

	private DriverLicenseDocument convertToDocument(DriverLicense driverLicense) {
		if(driverLicense == null)
			return null;

		DriverLicenseDocument driverLicenseDocument = new DriverLicenseDocument();
		driverLicenseDocument.licenseNumber = driverLicense.getLicenseNumber();
		driverLicenseDocument.issueDate = driverLicense.getIssueDate();
		driverLicenseDocument.expiryDate = driverLicense.getExpiryDate();
		return driverLicenseDocument;
	}

	public Customer convertFromDocument(CustomerDocument customerDocument) {
		return new Customer(
				(customerDocument.id != null) ? customerDocument.id.toString() : null,
				new CustomerDetails(
						customerDocument.firstName,
						customerDocument.lastName,
						customerDocument.dateOfBirth,
						customerDocument.fiscalCode,
						customerDocument.email,
						customerDocument.phoneNumber,
						this.convertFromDocument(customerDocument.driverLicense)
				),
				customerDocument.driverLicenseVerified,
				customerDocument.emailVerified
		);
	}

	private DriverLicense convertFromDocument(DriverLicenseDocument driverLicenseDocument) {
		if(driverLicenseDocument == null)
			return null;

		return new DriverLicense(
				driverLicenseDocument.licenseNumber,
				driverLicenseDocument.issueDate,
				driverLicenseDocument.expiryDate
		);
	}
}
