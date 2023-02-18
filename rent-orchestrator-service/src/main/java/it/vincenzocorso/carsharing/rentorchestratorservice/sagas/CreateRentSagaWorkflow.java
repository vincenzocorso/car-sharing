package it.vincenzocorso.carsharing.rentorchestratorservice.sagas;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface CreateRentSagaWorkflow {
    @WorkflowMethod
    void createRent(CreateRentSagaState state);

    @SignalMethod
    void handleVerifyCustomerResponse(boolean canRent);
}
