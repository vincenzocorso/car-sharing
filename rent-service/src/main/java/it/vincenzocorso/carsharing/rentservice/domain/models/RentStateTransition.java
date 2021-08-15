package it.vincenzocorso.carsharing.rentservice.domain.models;

import java.time.Instant;

public class RentStateTransition {
	private final Instant timestamp;
	private final RentState state;
	private final Integer sequenceNumber;

	public RentStateTransition(Instant timestamp, RentState state, Integer sequenceNumber) {
		this.timestamp = timestamp;
		this.state = state;
		this.sequenceNumber = sequenceNumber;
	}

	public static RentStateTransition to(RentState state, Integer sequenceNumber) {
		return new RentStateTransition(Instant.now(), state, sequenceNumber);
	}

	public Instant getTimestamp() {
		return this.timestamp;
	}

	public RentState getState() {
		return this.state;
	}

	public Integer getSequenceNumber() {
		return this.sequenceNumber;
	}
}
