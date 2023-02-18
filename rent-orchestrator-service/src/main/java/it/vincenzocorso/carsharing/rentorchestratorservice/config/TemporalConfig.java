package it.vincenzocorso.carsharing.rentorchestratorservice.config;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import it.vincenzocorso.carsharing.rentorchestratorservice.sagas.CreateRentSagaActivities;
import it.vincenzocorso.carsharing.rentorchestratorservice.sagas.CreateRentSagaWorkflowImpl;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
@Slf4j
public class TemporalConfig {
    @ConfigProperty(name = "temporal.host")
    String temporalHost;

    @ConfigProperty(name = "temporal.port")
    String temporalPort;

    @Produces @ApplicationScoped
    WorkflowClient workflowClient() {
        WorkflowServiceStubsOptions options = WorkflowServiceStubsOptions
                .newBuilder()
                .setTarget(this.temporalHost + ":" + this.temporalPort)
                .build();
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance(options);

        return WorkflowClient.newInstance(service);
    }

    @Produces @ApplicationScoped
    Worker worker(WorkflowClient workflowClient, CreateRentSagaActivities createRentSagaActivities) {
        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);

        String queueName = "RENT_SAGAS";
        Worker worker = factory.newWorker(queueName);
        worker.registerWorkflowImplementationTypes(CreateRentSagaWorkflowImpl.class);
        worker.registerActivitiesImplementations(createRentSagaActivities);

        factory.start();
        log.info("Temporal worker started for task queue: " + queueName);

        return worker;
    }
}
