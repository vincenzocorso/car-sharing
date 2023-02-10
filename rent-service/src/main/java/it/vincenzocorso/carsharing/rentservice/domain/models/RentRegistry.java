package it.vincenzocorso.carsharing.rentservice.domain.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This class saves the history of the state transitions of a rent
 */
public class RentRegistry {
	private final List<RentStateTransition> transitions;

	public RentRegistry(List<RentStateTransition> transitions) {
		this.transitions = transitions;
		this.transitions.sort(Comparator.comparing(RentStateTransition::getSequenceNumber));
	}

	/**
	 * Creates an empty registry
	 * @return An empty registry
	 */
	public static RentRegistry empty() {
		return new RentRegistry(new ArrayList<>());
	}

	/**
	 * Appends a new transition to the registry
	 * @param state The new state of the rent
	 */
	public void appendTransitionTo(RentState state) {
		Integer nextSequenceNumber = this.transitions.size() + 1;
		this.transitions.add(RentStateTransition.to(state, nextSequenceNumber));
	}

	/**
	 * Returns the current state of the rent
	 * @return The current state of the rent
	 */
	public RentState getCurrentState() {
		return this.getTransitions().get(this.transitions.size() - 1).getState();
	}

	public List<RentStateTransition> getTransitions() {
		return this.transitions;
	}
}
