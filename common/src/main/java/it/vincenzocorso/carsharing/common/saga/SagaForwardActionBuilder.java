package it.vincenzocorso.carsharing.common.saga;

import lombok.Getter;

import java.util.function.BiConsumer;

@Getter
public class SagaForwardActionBuilder<S extends SagaState> {
	private final SagaDefinitionBuilder<S> sagaDefinitionBuilder;
	private SagaStepAction<S> forwardAction;

	public SagaForwardActionBuilder(SagaDefinitionBuilder<S> sagaDefinitionBuilder) {
		this.sagaDefinitionBuilder = sagaDefinitionBuilder;
	}

	public <P extends SagaParticipantEndpoint> SagaBackwardActionBuilder<S> forward(String forwardActionName, P sagaParticipantEndpoint, BiConsumer<P, S> forwardAction) {
		this.forwardAction = new SagaStepAction<>(forwardActionName, sagaParticipantEndpoint, (BiConsumer<SagaParticipantEndpoint, S>) forwardAction);
		return new SagaBackwardActionBuilder<>(this.sagaDefinitionBuilder, this);
	}
}
