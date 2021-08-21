package it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa;

import it.vincenzocorso.carsharing.rentservice.adapters.persistence.RentWrapper;
import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import org.junit.jupiter.api.Test;

import static it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa.FakeRentEntity.*;
import static it.vincenzocorso.carsharing.rentservice.domain.FakeRent.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RentEntityMapperTest {
	private final RentEntityMapper rentEntityMapper = new RentEntityMapper();

	@Test
	void shouldConvertToEntity() {
		RentEntity rentEntity = this.rentEntityMapper.convertToEntity(RENT);

		FakeRentEntity.assertEqualsWithRent(rentEntity);
		assertNull(rentEntity.getVersion());
	}

	@Test
	void shouldSetEntityVersion() {
		RentEntity rentEntity = this.rentEntityMapper.convertToEntity(RENT_WRAPPER);

		assertEquals(RENT_VERSION, rentEntity.getVersion());
	}

	@Test
	void shouldConvertFromEntity() {
		Rent rent = this.rentEntityMapper.convertFromEntity(RENT_ENTITY);

		assertThat(rent).isInstanceOf(RentWrapper.class);
		assertEqualsWithRent(rent);
		assertEquals(RENT_VERSION, ((RentWrapper)rent).getVersion());
	}
}