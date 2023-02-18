package it.vincenzocorso.carsharing.rentorchestratorservice.sagas;

import io.temporal.activity.Activity;
import it.vincenzocorso.carsharing.common.messaging.commands.Command;
import it.vincenzocorso.carsharing.common.messaging.commands.CommandProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class CreateRentSagaActivitiesImpl implements CreateRentSagaActivities {
    private final CommandProducer commandProducer;

    @Override
    @Transactional
    public void verifyCustomer(String customerId) {
        String workflowId = Activity.getExecutionContext().getInfo().getWorkflowId();

        Command command = new VerifyCustomerCommand(customerId);
        String messageId = this.commandProducer.publish("customer-service-commands", "rent-orchestrator-service-response-channel", customerId, command);

        WorkflowCorrelation correlation = new WorkflowCorrelation(messageId, workflowId);
        correlation.persist();
    }

    @Override
    public void bookVehicle(String vehicleId, String customerId) {
        log.info("Booking vehicle: " + vehicleId + " for customer: " + customerId);
    }

    @Override
    @Transactional
    public void rejectRent(String rentId) {
        Command command = new RejectRentCommand(rentId);
        this.commandProducer.publish("rent-service-commands", rentId, command);
    }
}

record VerifyCustomerCommand(String customerId) implements Command {
    @Override
    public String getType() {
        return "VERIFY_CUSTOMER_COMMAND";
    }
}

record RejectRentCommand(String rentId) implements Command {
    @Override
    public String getType() {
        return "REJECT_RENT_COMMAND";
    }
}
