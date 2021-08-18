package it.vincenzocorso.carsharing.rentservice.adapters.web;

import it.vincenzocorso.carsharing.rentservice.api.web.RentResponse;
import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentState;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentStateTransition;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RentMapper {
	public RentResponse convertToDto(Rent rent) {
		return RentResponse.builder()
				.rentId(rent.getId())
				.customerId(rent.getDetails().getCustomerId())
				.vehicleId(rent.getDetails().getVehicleId())
				.state(rent.getCurrentState().toString())
				.acceptedAt(this.getTransitionInstant(rent, RentState.ACCEPTED))
				.startedAt(this.getTransitionInstant(rent, RentState.STARTED))
				.endedAt(this.getTransitionInstant(rent, RentState.ENDED))
				.build();
	}

	private Instant getTransitionInstant(Rent rent, RentState state) {
		return rent.getStateTransitions().stream()
				.filter(t -> t.getState().equals(state))
				.findFirst()
				.map(RentStateTransition::getTimestamp)
				.orElse(null);
	}
}
