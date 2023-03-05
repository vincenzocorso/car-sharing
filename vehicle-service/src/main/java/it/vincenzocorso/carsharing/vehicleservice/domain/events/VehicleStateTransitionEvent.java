package it.vincenzocorso.carsharing.vehicleservice.domain.events;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;

public record VehicleStateTransitionEvent(String oldState, String newState) implements DomainEvent {
	@Override
	public String getType() {
		return "VEHICLE_STATE_TRANSITION_EVENT";
	}
}
