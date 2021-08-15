package it.vincenzocorso.carsharing.common.events;

import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class EventEnvelope<E> {
	public final Instant timestamp;
	public final String aggregateId;
	public final E payload;

	public static <E> EventEnvelope<E> create(String aggregateId, E payload) {
		return new EventEnvelope<>(Instant.now(), aggregateId, payload);
	}
}
