package it.vincenzocorso.carsharing.rentorchestratorservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.common.converter.*;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import it.vincenzocorso.carsharing.common.saga.SagaState;
import it.vincenzocorso.carsharing.rentorchestratorservice.adapters.sagas.temporal.*;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Produces;

@ApplicationScoped
@Slf4j
public class TemporalConfig {
	@Produces @ApplicationScoped
	public WorkflowClient workflowClient() {
		WorkflowServiceStubsOptions options = WorkflowServiceStubsOptions
				.newBuilder()
				.setTarget("temporal:7233")
				.build();
		WorkflowServiceStubs service = WorkflowServiceStubs.newInstance(options);

		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixIn(SagaState.class, SagaStateJacksonMixIn.class);

		DataConverter converter = new DefaultDataConverter(new JacksonJsonPayloadConverter(mapper));
		WorkflowClientOptions clientOptions = WorkflowClientOptions
				.newBuilder()
				.setDataConverter(converter)
				.build();

		return WorkflowClient.newInstance(service,clientOptions);
	}

	@Produces @ApplicationScoped
	public Worker worker(
			WorkflowClient workflowClient,
			SagaActivities sagaActivities,
			SagaRepository sagaRepository) {
		WorkerFactory factory = WorkerFactory.newInstance(workflowClient);

		String queueName = "RENT_SAGAS";
		Worker worker = factory.newWorker(queueName);

		worker.addWorkflowImplementationFactory(SagaWorkflow.class, () -> new SagaWorkflowImpl(sagaRepository));

		worker.registerActivitiesImplementations(sagaActivities);

		factory.start();
		log.info("Temporal worker started for task queue: " + queueName);

		return worker;
	}
}
