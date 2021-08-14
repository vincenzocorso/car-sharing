package it.vincenzocorso.carsharing.common;

import java.util.List;

public class ResultWithEvents<R> {
	public final R result;
	public final List<DomainEvent> events;

	public ResultWithEvents(R result, List<DomainEvent> events) {
		this.result = result;
		this.events = events;
	}
}
