package it.vincenzocorso.carsharing.customerservice.domain;

import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import it.vincenzocorso.carsharing.customerservice.domain.models.CustomerDetails;
import it.vincenzocorso.carsharing.customerservice.domain.models.DriverLicense;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FakeCustomer {
	public static final String CUSTOMER_ID = "5349b4ddd2781d08c09890f3";

	public static final String FIRST_NAME = "Mario";
	public static final String LAST_NAME = "Rossi";
	public static final LocalDate DATE_OF_BIRTH = LocalDate.of(1997, 5, 9);
	public static final String FISCAL_CODE = "RSSMRA97E09G273L";
	public static final String EMAIL = "mariorossi97@gmail.com";
	public static final String PHONE_NUMBER = "+393278564190";
	public static final String LICENSE_NUMBER = "AB123FG";
	public static final LocalDate ISSUE_DATE = LocalDate.of(2017, 10, 4);
	public static final LocalDate EXPIRY_DATE = LocalDate.now().plusYears(1);
	public static final DriverLicense DRIVER_LICENSE = new DriverLicense(LICENSE_NUMBER, ISSUE_DATE, EXPIRY_DATE);
	public static final CustomerDetails CUSTOMER_DETAILS = makeCustomerDetails();

	public static final boolean DRIVER_LICENSE_VERIFIED = true;
	public static final boolean EMAIL_VERIFIED = true;

	public static final Customer CUSTOMER = new Customer(CUSTOMER_ID, CUSTOMER_DETAILS, DRIVER_LICENSE_VERIFIED, EMAIL_VERIFIED);

	public static final DriverLicense EXPIRED_DRIVER_LICENSE = new DriverLicense(LICENSE_NUMBER, ISSUE_DATE, LocalDate.now().minusDays(1));

	public static Customer customerInState(boolean driverLicenseVerified, boolean emailVerified) {
		return new Customer(CUSTOMER_ID, makeCustomerDetails(), driverLicenseVerified, emailVerified);
	}

	private static CustomerDetails makeCustomerDetails() {
		return CustomerDetails.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.dateOfBirth(DATE_OF_BIRTH)
				.fiscalCode(FISCAL_CODE)
				.email(EMAIL)
				.phoneNumber(PHONE_NUMBER)
				.driverLicense(DRIVER_LICENSE)
				.build();
	}

	public static Customer verifiedCustomer() {
		return customerInState(true, true);
	}

	public static void assertEqualsWithCustomer(Customer actualCustomer) {
		assertEquals(CUSTOMER_ID, actualCustomer.getId());
		assertEqualsWithCustomerDetails(actualCustomer.getCustomerDetails());
		assertEquals(DRIVER_LICENSE_VERIFIED, actualCustomer.isDriverLicenseVerified());
		assertEquals(EMAIL_VERIFIED, actualCustomer.isEmailVerified());
	}

	private static void assertEqualsWithCustomerDetails(CustomerDetails actualCustomerDetails) {
		assertEquals(FIRST_NAME, actualCustomerDetails.getFirstName());
		assertEquals(LAST_NAME, actualCustomerDetails.getLastName());
		assertEquals(DATE_OF_BIRTH, actualCustomerDetails.getDateOfBirth());
		assertEquals(FISCAL_CODE, actualCustomerDetails.getFiscalCode());
		assertEquals(EMAIL, actualCustomerDetails.getEmail());
		assertEquals(PHONE_NUMBER, actualCustomerDetails.getPhoneNumber());
		assertEqualsWithCustomerDriverLicense(actualCustomerDetails.getDriverLicense());
	}

	private static void assertEqualsWithCustomerDriverLicense(DriverLicense actualDriverLicense) {
		assertEquals(LICENSE_NUMBER, actualDriverLicense.getLicenseNumber());
		assertEquals(ISSUE_DATE, actualDriverLicense.getIssueDate());
		assertEquals(EXPIRY_DATE, actualDriverLicense.getExpiryDate());
	}
}
