package it.vincenzocorso.carsharing.common;

import java.time.Instant;

public class EventEnvelope<E extends DomainEvent> {
	public final Instant timestamp;
	public final String aggregateId;
	public final E payload;

	public EventEnvelope(Instant timestamp, String aggregateId, E payload) {
		this.timestamp = timestamp;
		this.aggregateId = aggregateId;
		this.payload = payload;
	}

	public static <E extends DomainEvent> EventEnvelope<E> create(String aggregateId, E payload) {
		return new EventEnvelope<>(Instant.now(), aggregateId, payload);
	}
}
