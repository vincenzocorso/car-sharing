package it.vincenzocorso.carsharing.common.spring.messaging.outbox.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.vincenzocorso.carsharing.common.exceptions.InternalServerException;
import it.vincenzocorso.carsharing.common.messaging.commands.Command;
import it.vincenzocorso.carsharing.common.messaging.commands.CommandProducer;
import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;
import it.vincenzocorso.carsharing.common.messaging.events.DomainEventProducer;
import it.vincenzocorso.carsharing.common.messaging.outbox.OutboxCommandHeaders;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class OutboxJpaMessageProducer implements CommandProducer, DomainEventProducer {
	private final OutboxJpaMessageRepository messageRepository;
	private final ObjectMapper objectMapper;

	@Override
	public void publish(String channel, String aggregateId, Command command) {
		String payload = this.encodePayload(command);
		OutboxMessageEntity outboxEvent = new OutboxMessageEntity(channel, aggregateId, payload);

		String encodedHeaders = this.encodeCommandHeaders(command.getResponseChannel(), outboxEvent.getMessageId(), command.getType());
		outboxEvent.setHeaders(encodedHeaders);

		OutboxMessageEntity savedOutboxEvent = this.messageRepository.save(outboxEvent);
		this.messageRepository.delete(savedOutboxEvent);
	}

	private String encodeCommandHeaders(String responseChannel, String messageId, String type) {
		try {
			Map<String, String> headers = new HashMap<>();
			headers.put(OutboxCommandHeaders.RESPONSE_CHANNEL, responseChannel);
			headers.put(OutboxCommandHeaders.MESSAGE_ID, messageId);
			headers.put(OutboxCommandHeaders.TYPE, type);
			return this.objectMapper.writeValueAsString(headers);
		} catch (Exception ex) {
			log.error("An error occurred during headers encoding: ", ex);
			throw new InternalServerException();
		}
	}

	@Override
	public void publish(String channel, String aggregateId, List<DomainEvent> event) {
	}

	private String encodePayload(Object message) {
		try {
			return this.objectMapper.writeValueAsString(message);
		} catch (Exception ex) {
			log.error("An error occurred during payload encoding", ex);
			throw new InternalServerException();
		}
	}
}
