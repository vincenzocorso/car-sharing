package it.vincenzocorso.carsharing.customerservice.domain.models;

import org.junit.jupiter.api.Test;

import static it.vincenzocorso.carsharing.customerservice.domain.FakeCustomer.*;
import static org.junit.jupiter.api.Assertions.*;

class DriverLicenseTest {
	@Test
	void shouldBeExpired() {
		assertTrue(EXPIRED_DRIVER_LICENSE.isExpired());
	}

	@Test
	void shouldNotBeExpired() {
		assertFalse(DRIVER_LICENSE.isExpired());
	}
}