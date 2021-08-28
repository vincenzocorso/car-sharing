package it.vincenzocorso.carsharing.common.saga;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SagaDefinitionBuilder<S extends SagaState> {
	private final List<SagaStep<S>> steps = new ArrayList<>();

	public static <S extends SagaState> SagaDefinitionBuilder<S> start(Class<S> sagaStateClass) {
		return new SagaDefinitionBuilder<>();
	}

	public SagaStepBuilder<S> step() {
		return new SagaStepBuilder<>(this);
	}

	void addStep(SagaStep<S> sagaStep) {
		this.steps.add(sagaStep);
	}

	SagaDefinition<S> build() {
		return new SagaDefinition<>(this.steps);
	}
}
