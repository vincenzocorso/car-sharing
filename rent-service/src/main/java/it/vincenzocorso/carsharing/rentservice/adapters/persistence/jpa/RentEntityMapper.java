package it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa;

import it.vincenzocorso.carsharing.rentservice.adapters.persistence.RentWrapper;
import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentDetails;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentState;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentStateTransition;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RentEntityMapper {
	public RentEntity convertToEntity(Rent rent) {
		RentEntity rentEntity = new RentEntity();
		rentEntity.setId(Optional.ofNullable(rent.getId())
				.map(UUID::fromString)
				.orElse(null));
		rentEntity.setCustomerId(rent.getDetails().customerId());
		rentEntity.setVehicleId(rent.getDetails().vehicleId());
		rentEntity.setCurrentState(rent.getCurrentState().toString());
		List<RentStateTransitionEntity> transitions = rent.getStateTransitions().stream()
				.map(t -> this.convertToEntity(rentEntity, t))
				.toList();
		rentEntity.setStateTransitions(transitions);
		if(rent instanceof RentWrapper rentWrapper)
			rentEntity.setVersion(rentWrapper.getVersion());
		return rentEntity;
	}

	private RentStateTransitionEntity convertToEntity(RentEntity rent, RentStateTransition transition) {
		RentStateTransitionEntity transitionEntity = new RentStateTransitionEntity();
		transitionEntity.setId(new RentStateTransitionId(rent, transition.getSequenceNumber()));
		transitionEntity.setState(transition.getState().toString());
		transitionEntity.setTimestamp(LocalDateTime.ofInstant(transition.getTimestamp(), ZoneOffset.UTC));
		return transitionEntity;
	}

	public Rent convertFromEntity(RentEntity rentEntity) {
		String rentId = Optional.ofNullable(rentEntity.getId())
				.map(UUID::toString)
				.orElse(null);
		RentDetails rentDetails = new RentDetails(rentEntity.getCustomerId(), rentEntity.getVehicleId());
		List<RentStateTransition> transitions = rentEntity.getStateTransitions().stream().map(this::convertFromEntity).collect(Collectors.toList());
		return new RentWrapper(rentId, rentDetails, transitions, rentEntity.getVersion());
	}

	private RentStateTransition convertFromEntity(RentStateTransitionEntity transitionEntity) {
		Instant timestamp = transitionEntity.getTimestamp().toInstant(ZoneOffset.UTC);
		RentState rentState = RentState.valueOf(transitionEntity.getState());
		Integer sequenceNumber = transitionEntity.getId().getSequenceNumber();
		return new RentStateTransition(timestamp, rentState, sequenceNumber);
	}
}
