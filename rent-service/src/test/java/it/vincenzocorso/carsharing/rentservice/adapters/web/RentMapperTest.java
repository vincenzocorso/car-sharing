package it.vincenzocorso.carsharing.rentservice.adapters.web;

import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static it.vincenzocorso.carsharing.rentservice.domain.models.RandomRent.*;
import static it.vincenzocorso.carsharing.rentservice.domain.models.RentState.*;
import static org.assertj.core.api.Assertions.assertThat;

class RentMapperTest {
	@InjectMocks
	private RentMapper rentMapper;

	@Test
	void shouldConvertToDto() {
		Rent rent = randomRent(ENDED);
		RentResponse expectedRentResponse = RentResponse.builder()
				.rentId(rent.getId())
				.customerId(rent.getDetails().customerId())
				.vehicleId(rent.getDetails().vehicleId())
				.state(ENDED.toString())
				.acceptedAt(rent.getStateTransitions().get(1).getTimestamp())
				.startedAt(rent.getStateTransitions().get(2).getTimestamp())
				.endedAt(rent.getStateTransitions().get(3).getTimestamp())
				.build();

		RentResponse actualRentResponse = this.rentMapper.convertToDto(rent);

		assertThat(actualRentResponse)
				.hasNoNullFieldsOrProperties()
				.usingRecursiveComparison()
				.isEqualTo(expectedRentResponse);
	}
}