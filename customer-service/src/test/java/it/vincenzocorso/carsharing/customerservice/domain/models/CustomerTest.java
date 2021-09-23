package it.vincenzocorso.carsharing.customerservice.domain.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static it.vincenzocorso.carsharing.customerservice.domain.FakeCustomer.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void shouldVerifyEmail(boolean isEmailVerified) {
		Customer customer = customerInState(false, isEmailVerified);

		assertDoesNotThrow(customer::verifyEmail);

		assertTrue(customer.isEmailVerified());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void shouldVerifyDriverLicense(boolean isDriverLicenseVerified) {
		Customer customer = customerInState(isDriverLicenseVerified, true);

		assertDoesNotThrow(customer::verifyDriverLicense);

		assertTrue(customer.isDriverLicenseVerified());
	}

	@Test
	void shouldBeAbleToRentAVehicle() {
		assertTrue(CUSTOMER.canRentAVehicle());
	}

	@ParameterizedTest
	@CsvSource({"false,false", "true,false", "false,true"})
	void shouldNotBeAbleToRentAVehicleWhenDriverLicenseOrEmailAreNotVerified(boolean isDriverLicenseVerified, boolean isEmailVerified) {
		Customer customer = customerInState(isDriverLicenseVerified, isEmailVerified);

		assertFalse(customer.canRentAVehicle());
	}

	@Test
	void shouldNotBeAbleToRentAVehicleWhenDriverLicenseIsExpired() {
		Customer customer = verifiedCustomer();
		customer.getCustomerDetails().setDriverLicense(EXPIRED_DRIVER_LICENSE);

		assertFalse(customer.canRentAVehicle());
	}
}