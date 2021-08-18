package it.vincenzocorso.carsharing.rentservice.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class RentStateTransition {
	private final Instant timestamp;
	private final RentState state;
	private final Integer sequenceNumber;

	public static RentStateTransition to(RentState state, Integer sequenceNumber) {
		return new RentStateTransition(Instant.now(), state, sequenceNumber);
	}
}
