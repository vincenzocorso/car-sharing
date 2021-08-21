package it.vincenzocorso.carsharing.rentservice.domain.models;

import it.vincenzocorso.carsharing.common.events.DomainEvent;
import it.vincenzocorso.carsharing.common.events.ResultWithEvents;
import it.vincenzocorso.carsharing.rentservice.api.events.RentCreatedEvent;
import it.vincenzocorso.carsharing.rentservice.api.events.RentStateTransitionEvent;
import it.vincenzocorso.carsharing.rentservice.domain.exceptions.IllegalRentStateTransitionException;

import java.util.List;

public class Rent {
	private String id;
	private final RentDetails details;
	private final RentRegistry transitionsRegistry;

	public Rent(String id, RentDetails details, List<RentStateTransition> transitions) {
		this.id = id;
		this.details = details;
		this.transitionsRegistry = new RentRegistry(transitions);
	}

	private Rent(RentDetails details) {
		this.details = details;
		this.transitionsRegistry = RentRegistry.empty();
	}

	public static ResultWithEvents<Rent> create(RentDetails rentDetails) {
		Rent rent = new Rent(rentDetails);
		rent.transitionsRegistry.appendTransitionTo(RentState.PENDING);
		DomainEvent domainEvent = new RentCreatedEvent(rentDetails.getCustomerId(), rentDetails.getVehicleId());
		return ResultWithEvents.of(rent, List.of(domainEvent));
	}

	public List<DomainEvent> reject() {
		RentState state = this.transitionsRegistry.getCurrentState();
		if(!state.equals(RentState.PENDING))
			throw new IllegalRentStateTransitionException();

		this.transitionsRegistry.appendTransitionTo(RentState.REJECTED);
		return List.of(new RentStateTransitionEvent(state.toString(), RentState.REJECTED.toString()));
	}

	public List<DomainEvent> accept() {
		RentState state = this.transitionsRegistry.getCurrentState();
		if(!state.equals(RentState.PENDING))
			throw new IllegalRentStateTransitionException();

		this.transitionsRegistry.appendTransitionTo(RentState.ACCEPTED);
		return List.of(new RentStateTransitionEvent(state.toString(), RentState.ACCEPTED.toString()));
	}

	public List<DomainEvent> cancel() {
		RentState state = this.transitionsRegistry.getCurrentState();
		if(!state.equals(RentState.ACCEPTED))
			throw new IllegalRentStateTransitionException();

		this.transitionsRegistry.appendTransitionTo(RentState.CANCELLED);
		return List.of(new RentStateTransitionEvent(state.toString(), RentState.CANCELLED.toString()));
	}

	public List<DomainEvent> start() {
		RentState state = this.transitionsRegistry.getCurrentState();
		if(!state.equals(RentState.ACCEPTED))
			throw new IllegalRentStateTransitionException();

		this.transitionsRegistry.appendTransitionTo(RentState.STARTED);
		return List.of(new RentStateTransitionEvent(state.toString(), RentState.STARTED.toString()));
	}

	public List<DomainEvent> end() {
		RentState state = this.transitionsRegistry.getCurrentState();
		if(!state.equals(RentState.STARTED))
			throw new IllegalRentStateTransitionException();

		this.transitionsRegistry.appendTransitionTo(RentState.ENDED);
		return List.of(new RentStateTransitionEvent(state.toString(), RentState.ENDED.toString()));
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RentDetails getDetails() {
		return this.details;
	}

	public RentState getCurrentState() {
		return this.transitionsRegistry.getCurrentState();
	}

	public List<RentStateTransition> getStateTransitions() {
		return this.transitionsRegistry.getTransitions();
	}
}
