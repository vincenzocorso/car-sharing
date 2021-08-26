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

	public SagaForwardActionBuilder<S> step() {
		return new SagaForwardActionBuilder<>(this);
	}

	public SagaRetriableStepBuilder<S> retriableSteps() {
		return new SagaRetriableStepBuilder<>(this);
	}

	public SagaDefinition<S> end() {
		return this.build();
	}

	void addStep(SagaStep<S> sagaStep) {
		this.steps.add(sagaStep);
	}

	SagaDefinition<S> build() {
		return new SagaDefinition<>(this.steps);
	}
}
