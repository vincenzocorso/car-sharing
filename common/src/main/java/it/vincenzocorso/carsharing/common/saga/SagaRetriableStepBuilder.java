package it.vincenzocorso.carsharing.common.saga;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SagaRetriableStepBuilder {
	private final SagaDefinitionBuilder sagaDefinitionBuilder;

	public <P extends SagaParticipantEndpoint> SagaRetriableStepBuilder forward(String actionName, P sagaParticipantEndpoint) {
		SagaStepAction retriableAction = new SagaStepAction(actionName, sagaParticipantEndpoint);
		SagaStep sagaStep = new SagaStep(retriableAction, null, SagaStepType.RETRIABLE);
		this.sagaDefinitionBuilder.addStep(sagaStep);
		return this;
	}

	public SagaDefinition end() {
		return this.sagaDefinitionBuilder.build();
	}
}
