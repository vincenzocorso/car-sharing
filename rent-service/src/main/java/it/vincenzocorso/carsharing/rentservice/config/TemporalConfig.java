package it.vincenzocorso.carsharing.rentservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.common.converter.*;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import it.vincenzocorso.carsharing.common.saga.SagaState;
import it.vincenzocorso.carsharing.rentservice.adapters.sagas.temporal.SagaActivities;
import it.vincenzocorso.carsharing.rentservice.adapters.sagas.temporal.SagaWorkflowImpl;
import it.vincenzocorso.carsharing.rentservice.adapters.sagas.temporal.SagaStateJacksonMixIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class TemporalConfig {
	@Bean
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

	@Bean
	public Worker worker(
			WorkflowClient workflowClient,
			SagaActivities sagaActivities) {
		WorkerFactory factory = WorkerFactory.newInstance(workflowClient);

		String queueName = "RENT_SAGAS";
		Worker worker = factory.newWorker(queueName);

		worker.registerWorkflowImplementationTypes(SagaWorkflowImpl.class);

		worker.registerActivitiesImplementations(sagaActivities);

		factory.start();
		log.info("Temporal worker started for task queue: " + queueName);

		return worker;
	}
}
