package it.vincenzocorso.carsharing.rentservice.domain.models;

import it.vincenzocorso.carsharing.rentservice.domain.exceptions.IllegalRentStateTransitionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static it.vincenzocorso.carsharing.rentservice.domain.FakeRent.*;
import static org.junit.jupiter.api.Assertions.*;

class RentTest {
	@Test
	void shouldCreateRentInPendingState() {
		Rent createdRent = Rent.create(RENT_DETAILS).result;

		assertEquals(RentState.PENDING, createdRent.getCurrentState());
	}

	@Test
	void shouldRejectRent() {
		Rent rent = rentInState(RentState.PENDING);

		rent.reject();

		assertEquals(RentState.REJECTED, rent.getCurrentState());
	}

	@ParameterizedTest
	@EnumSource(value = RentState.class, mode = EnumSource.Mode.EXCLUDE, names = {"PENDING"})
	void shouldNotRejectRent(RentState state) {
		Rent rent = rentInState(state);

		assertThrows(IllegalRentStateTransitionException.class, rent::reject);
	}

	@Test
	void shouldAcceptRent() {
		Rent rent = rentInState(RentState.PENDING);

		rent.accept();

		assertEquals(RentState.ACCEPTED, rent.getCurrentState());
	}

	@ParameterizedTest
	@EnumSource(value = RentState.class, mode = EnumSource.Mode.EXCLUDE, names = {"PENDING"})
	void shouldNotAcceptRent(RentState state) {
		Rent rent = rentInState(state);

		assertThrows(IllegalRentStateTransitionException.class, rent::accept);
	}

	@Test
	void shouldCancelRent() {
		Rent rent = rentInState(RentState.ACCEPTED);

		rent.cancel();

		assertEquals(RentState.CANCELLED, rent.getCurrentState());
	}

	@ParameterizedTest
	@EnumSource(value = RentState.class, mode = EnumSource.Mode.EXCLUDE, names = {"ACCEPTED"})
	void shouldNotCancelRent(RentState state) {
		Rent rent = rentInState(state);

		assertThrows(IllegalRentStateTransitionException.class, rent::cancel);
	}

	@Test
	void shouldStartRent() {
		Rent rent = rentInState(RentState.ACCEPTED);

		rent.start();

		assertEquals(RentState.STARTED, rent.getCurrentState());
	}

	@ParameterizedTest
	@EnumSource(value = RentState.class, mode = EnumSource.Mode.EXCLUDE, names = {"ACCEPTED"})
	void shouldNotStartRent(RentState state) {
		Rent rent = rentInState(state);

		assertThrows(IllegalRentStateTransitionException.class, rent::start);
	}

	@Test
	void shouldEndRent() {
		Rent rent = rentInState(RentState.STARTED);

		rent.end();

		assertEquals(RentState.ENDED, rent.getCurrentState());
	}

	@ParameterizedTest
	@EnumSource(value = RentState.class, mode = EnumSource.Mode.EXCLUDE, names = {"STARTED"})
	void shouldNotEndRent(RentState state) {
		Rent rent = rentInState(state);

		assertThrows(IllegalRentStateTransitionException.class, rent::end);
	}
}