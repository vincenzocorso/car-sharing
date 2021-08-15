package it.vincenzocorso.carsharing.common.events;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultWithEvents<R> {
	public final R result;
	public final List<DomainEvent> events;

	public static <D> ResultWithEvents<D> of(D result, List<DomainEvent> events) {
		return new ResultWithEvents<>(result, events);
	}
}
