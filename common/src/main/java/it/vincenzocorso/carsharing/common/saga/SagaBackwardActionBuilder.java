package it.vincenzocorso.carsharing.common.saga;

import lombok.AllArgsConstructor;

import java.util.function.BiConsumer;

@AllArgsConstructor
public class SagaBackwardActionBuilder<S extends SagaState> {
	private final SagaDefinitionBuilder<S> sagaDefinitionBuilder;
	private final SagaForwardActionBuilder<S> sagaForwardActionBuilder;

	public <P extends SagaParticipantEndpoint> SagaDefinitionBuilder<S> backward(String actionName, P sagaParticipantEndpoint, BiConsumer<P, S> backwardAction) {
		SagaStepAction<S> backwardStepAction = new SagaStepAction<>(actionName, sagaParticipantEndpoint, (BiConsumer<SagaParticipantEndpoint, S>) backwardAction);
		SagaStep<S> step = new SagaStep<>(this.sagaForwardActionBuilder.getForwardAction(), backwardStepAction, SagaStepType.COMPENSABLE);
		this.sagaDefinitionBuilder.addStep(step);
		return this.sagaDefinitionBuilder;
	}

	public SagaForwardActionBuilder<S> step() {
		SagaStep<S> step = new SagaStep<>(this.sagaForwardActionBuilder.getForwardAction(), null, SagaStepType.COMPENSABLE);
		this.sagaDefinitionBuilder.addStep(step);
		return new SagaForwardActionBuilder<>(this.sagaDefinitionBuilder);
	}

	public SagaRetriableStepBuilder<S> retriableSteps() {
		return new SagaRetriableStepBuilder<>(this.sagaDefinitionBuilder);
	}
}
