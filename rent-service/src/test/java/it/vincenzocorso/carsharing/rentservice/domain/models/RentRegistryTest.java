package it.vincenzocorso.carsharing.rentservice.domain.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.vincenzocorso.carsharing.rentservice.domain.FakeRent.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RentRegistryTest {
	@Test
	void shouldCreateOrderedRegistry() {
		List<RentStateTransition> unorderedTransitions = Arrays.asList(TRANSITION_3, TRANSITION_1, TRANSITION_4, TRANSITION_2);

		RentRegistry rentRegistry = new RentRegistry(unorderedTransitions);

		List<RentStateTransition> storedTransitions = rentRegistry.getTransitions();
		assertThat(storedTransitions).hasSameElementsAs(ORDERED_STATE_TRANSITIONS);
	}

	@Test
	void shouldAddOrder() {
		List<RentStateTransition> transitions = new ArrayList<>(List.of(TRANSITION_1, TRANSITION_2, TRANSITION_3));
		RentRegistry rentRegistry = new RentRegistry(transitions);

		rentRegistry.appendTransitionTo(RentState.ENDED);

		List<RentStateTransition> storedTransitions = rentRegistry.getTransitions();
		assertEquals(RentState.ENDED, rentRegistry.getCurrentState());
		assertEquals(4, rentRegistry.getTransitions().get(3).getSequenceNumber());
	}

	@Test
	void shouldAddOrderWhenEmpty() {
		RentRegistry rentRegistry = RentRegistry.empty();

		rentRegistry.appendTransitionTo(RentState.ACCEPTED);

		assertEquals(RentState.ACCEPTED, rentRegistry.getCurrentState());
		assertEquals(1, rentRegistry.getTransitions().size());
	}
}