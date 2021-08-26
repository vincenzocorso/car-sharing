package it.vincenzocorso.carsharing.common.saga;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class SagaStep<S extends SagaState> {
	private final SagaStepAction<S> forwardAction;
	private final SagaStepAction<S> backwardAction;
	private final SagaStepType type;
}
