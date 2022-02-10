package it.vincenzocorso.carsharing.rentservice.api.messaging.events;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentStateTransitionEvent implements DomainEvent {
	public final String oldState;
	public final String newState;

	@Override
	public String getType() {
		return "RENT_DOMAIN_EVENT";
	}
}
