package it.vincenzocorso.carsharing.rentorchestratorservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import it.vincenzocorso.carsharing.common.messaging.events.EventHeaders;
import it.vincenzocorso.carsharing.rentorchestratorservice.RentSagaOrchestrator;
import it.vincenzocorso.carsharing.rentorchestratorservice.sagas.CreateRentSagaState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
@Slf4j
@AllArgsConstructor
public class RentServiceEventHandler {
	private final ObjectMapper objectMapper;
	private final RentSagaOrchestrator rentSagaOrchestrator;

	@Incoming("rent-service-events")
	public CompletionStage<Void> dispatchMessage(Message<String> message) throws Exception {
		var metadata = message.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
		String type = new String(metadata.getHeaders().lastHeader(EventHeaders.TYPE).value());
		log.info("Received message of type: " + type);

		switch (type) {
			case "RENT_CREATED_EVENT" -> {
				RentCreatedEvent rentCreatedEvent = this.objectMapper.readValue(message.getPayload(), RentCreatedEvent.class);
				this.processRentCreatedEvent(message.withPayload(rentCreatedEvent));
			}
			default -> log.info("An event of type {} was ignored", type);
		}

		return message.ack();
	}

	private void processRentCreatedEvent(Message<RentCreatedEvent> message) {
		var metadata = message.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
		String rentId = new String(metadata.getHeaders().lastHeader(EventHeaders.AGGREGATE_ID).value());
		RentCreatedEvent rentCreatedEvent = message.getPayload();
		log.info("Processing event: " + rentCreatedEvent);

		CreateRentSagaState state = new CreateRentSagaState(rentId, rentCreatedEvent.customerId(), rentCreatedEvent.vehicleId());
		this.rentSagaOrchestrator.startCreateRentSaga(state);
	}
}

record RentCreatedEvent(String customerId, String vehicleId) {
}
