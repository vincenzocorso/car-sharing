package it.vincenzocorso.carsharing.rentservice.api.messaging.events;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentStateTransitionEvent implements RentDomainEvent {
	public final String oldState;
	public final String newState;

	@Override
	public String getType() {
		return "RENT_DOMAIN_EVENT";
	}
}
