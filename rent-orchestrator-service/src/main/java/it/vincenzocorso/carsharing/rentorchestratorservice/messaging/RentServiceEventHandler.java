package it.vincenzocorso.carsharing.rentorchestratorservice.messaging;

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
	private final RentSagaOrchestrator rentSagaOrchestrator;

	@Incoming("rent-service-events")
	public CompletionStage<Void> consume(Message<RentCreatedEvent> message) {
		var metadata = message.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
		String rentId = new String(metadata.getHeaders().lastHeader(EventHeaders.AGGREGATE_ID).value());
		RentCreatedEvent rentCreatedEvent = message.getPayload();
		log.info("RentCreateEvent (rentId " + rentId + ") arrived: " + rentCreatedEvent.customerId() + " " + rentCreatedEvent.vehicleId());

		CreateRentSagaState state = new CreateRentSagaState(rentId, rentCreatedEvent.customerId(), rentCreatedEvent.vehicleId());
		this.rentSagaOrchestrator.startCreateRentSaga(state);

		return message.ack();
	}
}

record RentCreatedEvent(String customerId, String vehicleId) {
}
