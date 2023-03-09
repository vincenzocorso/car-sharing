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
                state.rejectReason = "Customer cannot rent a vehicle";
                saga.compensate();
                return;
            }

            this.activities.bookVehicle(state.vehicleId);
            Workflow.await(Duration.ofMinutes(5), () -> state.hasVehicleBeenBooked != null);
            if(!state.hasVehicleBeenBooked) {
                state.rejectReason = "Vehicle cannot be booked";
                saga.compensate();
                return;
            }

            this.activities.acceptRent(state.rentId);
        } catch(Exception ex) {
            log.error("An exception occurred during the create rent saga: ", ex);
            state.rejectReason = "An error occurred while processing the request";
            saga.compensate();
        }
    }

    @Override
    public void handleVerifyCustomerResponse(boolean canRent) {
        this.state.canCustomerRent = canRent;
    }

    @Override
    public void handleBookVehicleResponse(boolean hasVehicleBeenBooked) {
        this.state.hasVehicleBeenBooked = hasVehicleBeenBooked;
    }
}
