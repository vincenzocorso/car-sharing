package it.vincenzocorso.carsharing.rentservice.domain.models;

import it.vincenzocorso.carsharing.rentservice.domain.exceptions.IllegalRentStateTransitionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static it.vincenzocorso.carsharing.rentservice.domain.models.RandomRent.*;
import static it.vincenzocorso.carsharing.rentservice.domain.models.RentState.*;
import static org.assertj.core.api.Assertions.*;

class RentTest {
	@Test
	void shouldCreateRentInPendingState() {
		RentDetails rentDetails = randomRentDetails();

		Rent createdRent = Rent.create(rentDetails).result;

		assertThat(createdRent.getCurrentState())
				.isEqualTo(PENDING);
	}

	@Test
	void shouldRejectRent() {
		Rent rent = randomRent(PENDING);

		rent.reject();

		assertThat(rent.getCurrentState())
				.isEqualTo(REJECTED);
	}

	@ParameterizedTest
	@EnumSource(value = RentState.class, mode = EnumSource.Mode.EXCLUDE, names = {"PENDING"})
	void shouldNotRejectRent(RentState state) {
		Rent rent = randomRent(state);

		assertThatExceptionOfType(IllegalRentStateTransitionException.class)
				.isThrownBy(rent::reject);
	}

	@Test
	void shouldAcceptRent() {
		Rent rent = randomRent(PENDING);

		rent.accept();

		assertThat(rent.getCurrentState())
				.isEqualTo(ACCEPTED);
	}

	@ParameterizedTest
	@EnumSource(value = RentState.class, mode = EnumSource.Mode.EXCLUDE, names = {"PENDING"})
	void shouldNotAcceptRent(RentState state) {
		Rent rent = randomRent(state);

		assertThatExceptionOfType(IllegalRentStateTransitionException.class)
				.isThrownBy(rent::accept);
	}

	@Test
	void shouldCancelRent() {
		Rent rent = randomRent(ACCEPTED);

		rent.cancel();

		assertThat(rent.getCurrentState())
				.isEqualTo(CANCELLED);
	}

	@ParameterizedTest
	@EnumSource(value = RentState.class, mode = EnumSource.Mode.EXCLUDE, names = {"ACCEPTED"})
	void shouldNotCancelRent(RentState state) {
		Rent rent = randomRent(state);

		assertThatExceptionOfType(IllegalRentStateTransitionException.class)
				.isThrownBy(rent::cancel);
	}

	@Test
	void shouldStartRent() {
		Rent rent = randomRent(ACCEPTED);

		rent.start();

		assertThat(rent.getCurrentState())
				.isEqualTo(STARTED);
	}

	@ParameterizedTest
	@EnumSource(value = RentState.class, mode = EnumSource.Mode.EXCLUDE, names = {"ACCEPTED"})
	void shouldNotStartRent(RentState state) {
		Rent rent = randomRent(state);

		assertThatExceptionOfType(IllegalRentStateTransitionException.class)
				.isThrownBy(rent::start);
	}

	@Test
	void shouldEndRent() {
		Rent rent = randomRent(RentState.STARTED);

		rent.end();

		assertThat(rent.getCurrentState())
				.isEqualTo(ENDED);
	}

	@ParameterizedTest
	@EnumSource(value = RentState.class, mode = EnumSource.Mode.EXCLUDE, names = {"STARTED"})
	void shouldNotEndRent(RentState state) {
		Rent rent = randomRent(state);

		assertThatExceptionOfType(IllegalRentStateTransitionException.class)
				.isThrownBy(rent::end);
	}
}