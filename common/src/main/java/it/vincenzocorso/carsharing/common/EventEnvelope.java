package it.vincenzocorso.carsharing.common;

import java.time.Instant;

public class EventEnvelope<E extends DomainEvent> {
	private final Instant timestamp;
	private final String aggregateId;
	private final E payload;

	public EventEnvelope(String aggregateId, E payload) {
		this.timestamp = Instant.now();
		this.aggregateId = aggregateId;
		this.payload = payload;
	}

	public Instant getTimestamp() {
		return this.timestamp;
	}

	public String getAggregateId() {
		return this.aggregateId;
	}

	public E getPayload() {
		return this.payload;
	}
}
