package it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa;

import it.vincenzocorso.carsharing.rentservice.adapters.persistence.RentWrapper;
import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentStateTransition;

import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

import static it.vincenzocorso.carsharing.rentservice.domain.FakeRent.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FakeRentEntity {
	public static final Long RENT_VERSION = 3L;
	public static final Rent RENT_WRAPPER = new RentWrapper(RENT_ID, RENT_DETAILS, ORDERED_STATE_TRANSITIONS, RENT_VERSION);

	public static final RentEntity RENT_ENTITY = new RentEntityMapper().convertToEntity(RENT_WRAPPER);

	public static void assertEqualsWithRent(RentEntity actualRentEntity) {
		assertEquals(RENT_ID, Optional.ofNullable(actualRentEntity.getId()).map(UUID::toString).orElse(null));
		assertEquals(CUSTOMER_ID, actualRentEntity.getCustomerId());
		assertEquals(VEHICLE_ID, actualRentEntity.getVehicleId());
		for(RentStateTransition expectedTransition : ORDERED_STATE_TRANSITIONS) {
			RentStateTransitionEntity actualTransition = actualRentEntity.getStateTransitions().stream()
					.filter(t -> t.getId().getSequenceNumber().equals(expectedTransition.getSequenceNumber()))
					.findFirst()
					.orElse(null);
			assertNotNull(actualTransition);
			assertEquals(expectedTransition.getState().toString(), actualTransition.getState());
			assertEquals(expectedTransition.getTimestamp(), actualTransition.getTimestamp().toInstant(ZoneOffset.UTC));
		}
	}
}
