package it.vincenzocorso.carsharing.rentorchestratorservice;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowException;
import io.temporal.client.WorkflowOptions;
import io.temporal.worker.Worker;
import it.vincenzocorso.carsharing.common.exceptions.InternalServerException;
import it.vincenzocorso.carsharing.rentorchestratorservice.sagas.CreateRentSagaState;
import it.vincenzocorso.carsharing.rentorchestratorservice.sagas.CreateRentSagaWorkflow;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class RentSagaOrchestrator {
    private final WorkflowClient workflowClient;
    private final Worker worker;

    public void startCreateRentSaga(CreateRentSagaState state) {
        WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(this.worker.getTaskQueue()).build();
        CreateRentSagaWorkflow workflow = this.workflowClient.newWorkflowStub(CreateRentSagaWorkflow.class, options);
        try {
            WorkflowClient.start(() -> workflow.createRent(state));
        } catch (WorkflowException ex) {
            log.error("Workflow error: ", ex);
            throw new InternalServerException();
        }
    }
}
