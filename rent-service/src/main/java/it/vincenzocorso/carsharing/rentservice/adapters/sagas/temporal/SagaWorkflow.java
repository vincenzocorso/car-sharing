package it.vincenzocorso.carsharing.rentservice.adapters.sagas.temporal;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import it.vincenzocorso.carsharing.common.saga.SagaState;

@WorkflowInterface
public interface SagaWorkflow {
	@WorkflowMethod
	void startSaga(String sagaName, SagaState state);

	@SignalMethod
	void notifyMessageResponse(String messageName, boolean compensation);
}