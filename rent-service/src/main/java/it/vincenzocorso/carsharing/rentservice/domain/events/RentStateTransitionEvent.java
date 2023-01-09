package it.vincenzocorso.carsharing.rentservice.domain.events;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;

public record RentStateTransitionEvent(String oldState, String newState) implements DomainEvent {
	@Override
	public String getType() {
		return "RENT_DOMAIN_EVENT";
	}
}
