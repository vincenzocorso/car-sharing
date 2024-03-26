package it.vincenzocorso.carsharing.rentservice.domain.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static it.vincenzocorso.carsharing.rentservice.domain.models.RandomRent.*;
import static it.vincenzocorso.carsharing.rentservice.domain.models.RentState.*;
import static org.assertj.core.api.Assertions.*;

class RentRegistryTest {
	@Test
	void shouldCreateOrderedRegistry() {
		List<RentStateTransition> orderedTransitions = randomRentStateTransitionList(CANCELLED);
		List<RentStateTransition> unorderedTransitions = new ArrayList<>(orderedTransitions);
		Collections.shuffle(unorderedTransitions);

		RentRegistry rentRegistry = new RentRegistry(unorderedTransitions);

		List<RentStateTransition> storedTransitions = rentRegistry.getTransitions();
		assertThat(storedTransitions).hasSameElementsAs(orderedTransitions);
	}

	@Test
	void shouldAddInOrder() {
		RentRegistry rentRegistry = randomRentRegistry(STARTED);

		rentRegistry.appendTransitionTo(RentState.ENDED);

		assertThat(rentRegistry.getCurrentState()).isEqualTo(ENDED);
		assertThat(rentRegistry.getTransitions().get(3).getSequenceNumber()).isEqualTo(4);
	}

	@Test
	void shouldAddInOrderWhenEmpty() {
		RentRegistry rentRegistry = RentRegistry.empty();

		rentRegistry.appendTransitionTo(RentState.ACCEPTED);

		assertThat(rentRegistry.getCurrentState()).isEqualTo(ACCEPTED);
		assertThat(rentRegistry.getTransitions()).hasSize(1);
	}
}