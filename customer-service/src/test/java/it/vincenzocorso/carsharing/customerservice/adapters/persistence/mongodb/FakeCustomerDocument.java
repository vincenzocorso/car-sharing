package it.vincenzocorso.carsharing.customerservice.adapters.persistence.mongodb;

import org.bson.types.ObjectId;

import static it.vincenzocorso.carsharing.customerservice.domain.FakeCustomer.*;
import static org.junit.jupiter.api.Assertions.*;

public class FakeCustomerDocument {
	public static final DriverLicenseDocument DRIVER_LICENSE_DOCUMENT = new DriverLicenseDocument();
	static {
		DRIVER_LICENSE_DOCUMENT.licenseNumber = LICENSE_NUMBER;
		DRIVER_LICENSE_DOCUMENT.issueDate = ISSUE_DATE;
		DRIVER_LICENSE_DOCUMENT.expiryDate = EXPIRY_DATE;
	}

	public static final CustomerDocument CUSTOMER_DOCUMENT = new CustomerDocument();
	static {
		CUSTOMER_DOCUMENT.id = new ObjectId(CUSTOMER_ID);
		CUSTOMER_DOCUMENT.firstName = FIRST_NAME;
		CUSTOMER_DOCUMENT.lastName = LAST_NAME;
		CUSTOMER_DOCUMENT.dateOfBirth = DATE_OF_BIRTH;
		CUSTOMER_DOCUMENT.fiscalCode = FISCAL_CODE;
		CUSTOMER_DOCUMENT.email = EMAIL;
		CUSTOMER_DOCUMENT.phoneNumber = PHONE_NUMBER;
		CUSTOMER_DOCUMENT.driverLicense = DRIVER_LICENSE_DOCUMENT;
		CUSTOMER_DOCUMENT.driverLicenseVerified = DRIVER_LICENSE_VERIFIED;
		CUSTOMER_DOCUMENT.emailVerified = EMAIL_VERIFIED;
	}

	public static void assertEqualsWithCustomer(CustomerDocument actualCustomerDocument) {
		assertEquals(CUSTOMER_ID, actualCustomerDocument.id.toString());
		assertEquals(FIRST_NAME, actualCustomerDocument.firstName);
		assertEquals(LAST_NAME, actualCustomerDocument.lastName);
		assertEquals(DATE_OF_BIRTH, actualCustomerDocument.dateOfBirth);
		assertEquals(FISCAL_CODE, actualCustomerDocument.fiscalCode);
		assertEquals(EMAIL, actualCustomerDocument.email);
		assertEquals(PHONE_NUMBER, actualCustomerDocument.phoneNumber);
		assertEquals(LICENSE_NUMBER, actualCustomerDocument.driverLicense.licenseNumber);
		assertEquals(ISSUE_DATE, actualCustomerDocument.driverLicense.issueDate);
		assertEquals(EXPIRY_DATE, actualCustomerDocument.driverLicense.expiryDate);
	}
}
