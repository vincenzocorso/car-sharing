package it.vincenzocorso.carsharing.common.saga;

import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;

@RequiredArgsConstructor
public class SagaStepBuilder<S extends SagaState> {
	private final SagaDefinitionBuilder<S> sagaDefinitionBuilder;
	private SagaStepAction<S> forwardAction;
	private SagaStepAction<S> backwardAction;

	public <P extends SagaParticipantEndpoint> SagaStepBuilder<S> forward(String forwardActionName, P sagaParticipantEndpoint, BiConsumer<P, S> forwardAction) {
		this.forwardAction = new SagaStepAction<>(forwardActionName, sagaParticipantEndpoint, (BiConsumer<SagaParticipantEndpoint, S>) forwardAction);
		return this;
	}

	public <P extends SagaParticipantEndpoint> SagaStepBuilder<S> backward(String actionName, P sagaParticipantEndpoint, BiConsumer<P, S> backwardAction) {
		this.backwardAction = new SagaStepAction<>(actionName, sagaParticipantEndpoint, (BiConsumer<SagaParticipantEndpoint, S>) backwardAction);
		return this;
	}

	public SagaStepBuilder<S> step() {
		SagaStep<S> step = new SagaStep<>(this.forwardAction, this.backwardAction, SagaStepType.COMPENSABLE);
		this.sagaDefinitionBuilder.addStep(step);
		return new SagaStepBuilder<>(this.sagaDefinitionBuilder);
	}

	public SagaRetriableStepBuilder<S> retriableSteps() {
		SagaStep<S> step = new SagaStep<>(this.forwardAction, this.backwardAction, SagaStepType.COMPENSABLE);
		this.sagaDefinitionBuilder.addStep(step);
		return new SagaRetriableStepBuilder<>(this.sagaDefinitionBuilder);
	}

	public SagaDefinition<S> end() {
		SagaStep<S> step = new SagaStep<>(this.forwardAction, this.backwardAction, SagaStepType.COMPENSABLE);
		this.sagaDefinitionBuilder.addStep(step);
		return this.sagaDefinitionBuilder.build();
	}
}
