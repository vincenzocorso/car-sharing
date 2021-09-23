package it.vincenzocorso.carsharing.customerservice.adapters.web;

import it.vincenzocorso.carsharing.customerservice.api.web.CustomerResponse;
import it.vincenzocorso.carsharing.customerservice.api.web.DriverLicenseDetails;
import it.vincenzocorso.carsharing.customerservice.api.web.RegisterCustomerRequest;

import static it.vincenzocorso.carsharing.customerservice.domain.FakeCustomer.*;

public class FakeCustomerDto {
	public static final RegisterCustomerRequest REGISTER_CUSTOMER_REQUEST = RegisterCustomerRequest.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.dateOfBirth(DATE_OF_BIRTH)
				.fiscalCode(FISCAL_CODE)
				.email(EMAIL)
				.phoneNumber(PHONE_NUMBER)
				.driverLicense(new DriverLicenseDetails(LICENSE_NUMBER, ISSUE_DATE, EXPIRY_DATE))
				.build();

	public static final CustomerResponse CUSTOMER_RESPONSE = CustomerResponse.builder()
				.customerId(CUSTOMER_ID)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.dateOfBirth(DATE_OF_BIRTH)
				.fiscalCode(FISCAL_CODE)
				.email(EMAIL)
				.phoneNumber(PHONE_NUMBER)
				.driverLicense(new DriverLicenseDetails(LICENSE_NUMBER, ISSUE_DATE, EXPIRY_DATE))
				.build();
}
