package it.vincenzocorso.carsharing.rentorchestratorservice.sagas;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class CreateRentSagaWorkflowImpl implements CreateRentSagaWorkflow {
    private final ActivityOptions activityOptions =
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofHours(1))
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(1).build())
                    .build();
    private final CreateRentSagaActivities activities = Workflow.newActivityStub(CreateRentSagaActivities.class, this.activityOptions);

    private CreateRentSagaState state;

    @Override
    public void createRent(CreateRentSagaState state) {
        Saga.Options sagaOptions = new Saga.Options.Builder().setParallelCompensation(true).build();
        Saga saga = new Saga(sagaOptions);
        this.state = state;
        try {
            saga.addCompensation(this.activities::rejectRent, state.rentId);

            this.activities.verifyCustomer(state.customerId);
            Workflow.await(Duration.ofMinutes(5), () -> state.canCustomerRent != null);
            if(!state.canCustomerRent) {
                saga.compensate();
            }
        } catch(Exception ex) {
            log.error("An exception occurred during the create rent saga: ", ex);
            saga.compensate();
        }
    }

    @Override
    public void handleVerifyCustomerResponse(boolean canRent, String rejectReason) {
        this.state.canCustomerRent = canRent;
        if(!canRent) {
            this.state.rejectReason = rejectReason;
        }
    }
}
