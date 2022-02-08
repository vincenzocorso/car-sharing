package it.vincenzocorso.carsharing.common.saga;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SagaDefinitionBuilder {
	private final List<SagaStep> steps = new ArrayList<>();

	public static SagaDefinitionBuilder start() {
		return new SagaDefinitionBuilder();
	}

	public SagaStepBuilder step() {
		return new SagaStepBuilder(this);
	}

	void addStep(SagaStep sagaStep) {
		this.steps.add(sagaStep);
	}

	SagaDefinition build() {
		return new SagaDefinition(this.steps);
	}
}
