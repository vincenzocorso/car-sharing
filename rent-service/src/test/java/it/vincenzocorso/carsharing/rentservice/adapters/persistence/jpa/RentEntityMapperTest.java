package it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa;

import it.vincenzocorso.carsharing.rentservice.adapters.persistence.RentWrapper;
import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import org.junit.jupiter.api.Test;

import static it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa.RandomRentEntity.*;
import static it.vincenzocorso.carsharing.rentservice.domain.models.RandomRent.randomRent;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RentEntityMapperTest {
	private final RentEntityMapper rentEntityMapper = new RentEntityMapper();

	@Test
	void shouldConvertToEntity() {
		Rent rent = randomRent();

		RentEntity rentEntity = this.rentEntityMapper.convertToEntity(rent);

		assertEqualsWithRent(rent, rentEntity);
	}

	@Test
	void shouldSetEntityVersion() {
		RentWrapper rentWrapper = randomRentWrapper();

		RentEntity rentEntity = this.rentEntityMapper.convertToEntity(rentWrapper);

		assertEquals(rentWrapper.getVersion(), rentEntity.getVersion());
	}

	@Test
	void shouldConvertFromEntity() {
		RentEntity rentEntity = randomRentEntity();

		Rent rent = this.rentEntityMapper.convertFromEntity(rentEntity);

		assertThat(rent).isInstanceOf(RentWrapper.class);
		assertEqualsWithRentEntity(rentEntity, rent);
	}
}