package it.vincenzocorso.carsharing.common.events;

import java.util.List;

public class ResultWithEvents<R> {
	public final R result;
	public final List<DomainEvent> events;

	private ResultWithEvents(R result, List<DomainEvent> events) {
		this.result = result;
		this.events = events;
	}

	public static <D> ResultWithEvents<D> of(D result, List<DomainEvent> events) {
		return new ResultWithEvents<>(result, events);
	}
}
