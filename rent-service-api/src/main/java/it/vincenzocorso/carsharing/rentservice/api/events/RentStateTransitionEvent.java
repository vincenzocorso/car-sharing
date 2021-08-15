package it.vincenzocorso.carsharing.rentservice.api.events;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentStateTransitionEvent implements RentDomainEvent {
	public final String oldState;
	public final String newState;
}
