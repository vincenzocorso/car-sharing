package it.vincenzocorso.carsharing.rentorchestratorservice.adapters.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import it.vincenzocorso.carsharing.common.messaging.events.EventHeaders;
import it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.in.RentSagaOrchestrator;
import it.vincenzocorso.carsharing.rentservice.api.messaging.RentMessagingChannels;
import it.vincenzocorso.carsharing.rentservice.api.messaging.events.RentCreatedEvent;
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
	private final ObjectMapper objectMapper;

	@Incoming(RentMessagingChannels.EVENT_CHANNEL)
	public CompletionStage<Void> consume(Message<RentCreatedEvent> message) {
		var metadata = message.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
		String aggregateId = new String(metadata.getHeaders().lastHeader(EventHeaders.AGGREGATE_ID).value());

		RentCreatedEvent rentCreatedEvent = message.getPayload();
		log.info("RentCreateEvent (rentId " + aggregateId + ") arrived: " + rentCreatedEvent.customerId + " " + rentCreatedEvent.vehicleId);
		this.rentSagaOrchestrator.startCreateRentSaga(aggregateId, rentCreatedEvent.customerId, rentCreatedEvent.vehicleId);

		return message.ack();
	}
}
