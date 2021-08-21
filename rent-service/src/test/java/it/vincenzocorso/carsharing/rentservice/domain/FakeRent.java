package it.vincenzocorso.carsharing.rentservice.domain;

import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentDetails;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentState;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentStateTransition;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeRent {
	public static final String RENT_ID = "RNT-ATGH-0453-ABCD";

	public static final String CUSTOMER_ID = "CST-ABCD-1234-EFGH";
	public static final String VEHICLE_ID = "VH-4567-RTYU-8901";
	public static final RentDetails RENT_DETAILS = new RentDetails(CUSTOMER_ID, VEHICLE_ID);

	public static final RentState TRANSITION_1_STATE = RentState.PENDING;
	public static final Instant TRANSITION_1_TIMESTAMP = Instant.from(LocalDateTime.of(2021, 8, 14, 14, 3, 2).atZone(ZoneId.of("UTC")));
	public static final RentStateTransition TRANSITION_1 = new RentStateTransition(TRANSITION_1_TIMESTAMP, TRANSITION_1_STATE, 1);

	public static final RentState TRANSITION_2_STATE = RentState.ACCEPTED;
	public static final Instant TRANSITION_2_TIMESTAMP = Instant.from(LocalDateTime.of(2021, 8, 14, 14, 3, 10).atZone(ZoneId.of("UTC")));
	public static final RentStateTransition TRANSITION_2 = new RentStateTransition(TRANSITION_2_TIMESTAMP, TRANSITION_2_STATE, 2);

	public static final RentState TRANSITION_3_STATE = RentState.STARTED;
	public static final Instant TRANSITION_3_TIMESTAMP = Instant.from(LocalDateTime.of(2021, 8, 14, 14, 10, 52).atZone(ZoneId.of("UTC")));
	public static final RentStateTransition TRANSITION_3 = new RentStateTransition(TRANSITION_3_TIMESTAMP, TRANSITION_3_STATE, 3);

	public static final RentState TRANSITION_4_STATE = RentState.ENDED;
	public static final Instant TRANSITION_4_TIMESTAMP = Instant.from(LocalDateTime.of(2021, 8, 14, 15, 32, 40).atZone(ZoneId.of("UTC")));
	public static final RentStateTransition TRANSITION_4 = new RentStateTransition(TRANSITION_4_TIMESTAMP, TRANSITION_4_STATE, 4);

	public static final List<RentStateTransition> ORDERED_STATE_TRANSITIONS = Arrays.asList(TRANSITION_1, TRANSITION_2, TRANSITION_3, TRANSITION_4);

	public static final Rent RENT = new Rent(RENT_ID, RENT_DETAILS, ORDERED_STATE_TRANSITIONS);

	public static Rent rentInState(RentState state) {
		List<RentStateTransition> transitions = new ArrayList<>();
		int counter = 1;

		transitions.add(new RentStateTransition(getFakeInstant(counter), RentState.PENDING, counter++));
		if(state.equals(RentState.PENDING))
			return makeRent(transitions);

		if(state.equals(RentState.ACCEPTED) || state.equals(RentState.REJECTED)) {
			transitions.add(new RentStateTransition(getFakeInstant(counter), state, counter));
			return makeRent(transitions);
		}
		transitions.add(new RentStateTransition(getFakeInstant(counter), RentState.ACCEPTED, counter++));

		if(state.equals(RentState.CANCELLED)) {
			transitions.add(new RentStateTransition(getFakeInstant(counter), state, counter));
			return makeRent(transitions);
		}

		transitions.add(new RentStateTransition(getFakeInstant(counter), RentState.STARTED, counter++));
		if(state.equals(RentState.STARTED))
			return makeRent(transitions);

		transitions.add(new RentStateTransition(getFakeInstant(counter), RentState.ENDED, counter));
		return makeRent(transitions);
	}

	private static Instant getFakeInstant(int counter) {
		return Instant.from(LocalDateTime.of(2021, 8, 14, 14, 3, counter).atZone(ZoneId.of("UTC")));
	}

	private static Rent makeRent(List<RentStateTransition> transitions) {
		RentDetails rentDetails = new RentDetails(CUSTOMER_ID, VEHICLE_ID);
		return new Rent(RENT_ID, rentDetails, transitions);
	}
}
