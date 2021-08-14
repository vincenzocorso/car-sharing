package it.vincenzocorso.carsharing.rentservice.api;

public class RentStateTransitionEvent implements RentDomainEvent {
	public final String oldState;
	public final String newState;

	public RentStateTransitionEvent(String oldState, String newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
}
