package it.vincenzocorso.carsharing.rentservice.domain.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RentRegistry {
	private final List<RentStateTransition> transitions;

	public RentRegistry(List<RentStateTransition> transitions) {
		this.transitions = transitions;
		this.transitions.sort(Comparator.comparing(RentStateTransition::getSequenceNumber));
	}

	public static RentRegistry empty() {
		return new RentRegistry(new ArrayList<>());
	}

	public void appendTransitionTo(RentState state) {
		Integer nextSequenceNumber = this.transitions.size() + 1;
		this.transitions.add(RentStateTransition.to(state, nextSequenceNumber));
	}

	public RentState getCurrentState() {
		return this.getTransitions().get(this.transitions.size() - 1).getState();
	}

	public List<RentStateTransition> getTransitions() {
		return this.transitions;
	}
}
