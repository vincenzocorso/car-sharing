package it.vincenzocorso.carsharing.common.saga;

import lombok.AllArgsConstructor;

import java.util.function.BiConsumer;

@AllArgsConstructor
public class SagaRetriableStepBuilder<S extends SagaState> {
	private final SagaDefinitionBuilder<S> sagaDefinitionBuilder;

	public <P extends SagaParticipantEndpoint> SagaRetriableStepBuilder<S> forward(String actionName, P sagaParticipantEndpoint, BiConsumer<P, S> forwardAction) {
		SagaStepAction<S> retriableAction = new SagaStepAction<>(actionName, sagaParticipantEndpoint, (BiConsumer<SagaParticipantEndpoint, S>) forwardAction);
		SagaStep<S> sagaStep = new SagaStep<>(retriableAction, null, SagaStepType.RETRIABLE);
		this.sagaDefinitionBuilder.addStep(sagaStep);
		return this;
	}

	public SagaDefinition<S> end() {
		return this.sagaDefinitionBuilder.build();
	}
}
