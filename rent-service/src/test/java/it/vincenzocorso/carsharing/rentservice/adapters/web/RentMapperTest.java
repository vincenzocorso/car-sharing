package it.vincenzocorso.carsharing.rentservice.adapters.web;

import it.vincenzocorso.carsharing.rentservice.api.web.RentResponse;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static it.vincenzocorso.carsharing.rentservice.domain.FakeRent.*;

class RentMapperTest {
	private final RentMapper rentMapper = new RentMapper();

	@Test
	void shouldConvertToDto() {
		RentResponse expectedRentResponse = RentResponse.builder()
				.rentId(RENT_ID)
				.customerId(CUSTOMER_ID)
				.vehicleId(VEHICLE_ID)
				.state(RentState.ENDED.toString())
				.acceptedAt(TRANSITION_2_TIMESTAMP)
				.startedAt(TRANSITION_3_TIMESTAMP)
				.endedAt(TRANSITION_4_TIMESTAMP)
				.build();

		RentResponse actualRentResponse = this.rentMapper.convertToDto(RENT);

		Assertions.assertThat(actualRentResponse)
				.usingRecursiveComparison()
				.isEqualTo(expectedRentResponse);
	}
}