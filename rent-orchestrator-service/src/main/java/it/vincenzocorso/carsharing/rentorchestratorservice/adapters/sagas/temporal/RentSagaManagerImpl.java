package it.vincenzocorso.carsharing.rentorchestratorservice.adapters.sagas.temporal;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowException;
import io.temporal.client.WorkflowOptions;
import io.temporal.worker.Worker;
import it.vincenzocorso.carsharing.common.exceptions.InternalServerException;
import it.vincenzocorso.carsharing.common.saga.Saga;
import it.vincenzocorso.carsharing.common.saga.SagaState;
import it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.out.RentSagaManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Slf4j
@AllArgsConstructor
public class RentSagaManagerImpl implements RentSagaManager {
	private final WorkflowClient workflowClient;
	private final Worker worker;

	@Override
	public void startSaga(Saga saga, SagaState sagaState) {
		WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(this.worker.getTaskQueue()).build();
		SagaWorkflow workflow = this.workflowClient.newWorkflowStub(SagaWorkflow.class, options);
		try {
			WorkflowClient.start(() -> workflow.startSaga(saga.getName(), sagaState));
		} catch (WorkflowException ex) {
			log.error("Workflow error: ", ex);
			throw new InternalServerException();
		}
	}
}
