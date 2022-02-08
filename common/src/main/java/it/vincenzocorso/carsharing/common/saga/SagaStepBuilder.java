package it.vincenzocorso.carsharing.common.saga;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SagaStepBuilder {
	private final SagaDefinitionBuilder sagaDefinitionBuilder;
	private SagaStepAction forwardAction;
	private SagaStepAction backwardAction;

	public <P extends SagaParticipantEndpoint> SagaStepBuilder forward(String forwardActionName, P sagaParticipantEndpoint) {
		this.forwardAction = new SagaStepAction(forwardActionName, sagaParticipantEndpoint);
		return this;
	}

	public <P extends SagaParticipantEndpoint> SagaStepBuilder backward(String actionName, P sagaParticipantEndpoint) {
		this.backwardAction = new SagaStepAction(actionName, sagaParticipantEndpoint);
		return this;
	}

	public SagaStepBuilder step() {
		SagaStep step = new SagaStep(this.forwardAction, this.backwardAction, SagaStepType.COMPENSABLE);
		this.sagaDefinitionBuilder.addStep(step);
		return new SagaStepBuilder(this.sagaDefinitionBuilder);
	}

	public SagaRetriableStepBuilder retriableSteps() {
		SagaStep step = new SagaStep(this.forwardAction, this.backwardAction, SagaStepType.COMPENSABLE);
		this.sagaDefinitionBuilder.addStep(step);
		return new SagaRetriableStepBuilder(this.sagaDefinitionBuilder);
	}

	public SagaDefinition end() {
		SagaStep step = new SagaStep(this.forwardAction, this.backwardAction, SagaStepType.COMPENSABLE);
		this.sagaDefinitionBuilder.addStep(step);
		return this.sagaDefinitionBuilder.build();
	}
}
