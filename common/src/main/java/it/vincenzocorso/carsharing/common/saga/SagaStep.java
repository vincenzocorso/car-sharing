package it.vincenzocorso.carsharing.common.saga;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SagaStep {
	private final SagaStepAction forwardAction;
	private final SagaStepAction backwardAction;
	private final SagaStepType type;
}
