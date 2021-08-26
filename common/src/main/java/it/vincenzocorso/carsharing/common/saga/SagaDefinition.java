package it.vincenzocorso.carsharing.common.saga;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SagaDefinition<S extends SagaState> {
	private final List<SagaStep<S>> steps;
}
