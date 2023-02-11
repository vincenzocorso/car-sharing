package it.vincenzocorso.carsharing.common.messaging.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.vincenzocorso.carsharing.common.exceptions.InternalServerException;
import it.vincenzocorso.carsharing.common.messaging.commands.Command;
import it.vincenzocorso.carsharing.common.messaging.commands.CommandProducer;
import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;
import it.vincenzocorso.carsharing.common.messaging.events.DomainEventProducer;
import it.vincenzocorso.carsharing.common.messaging.commands.CommandHeaders;
import it.vincenzocorso.carsharing.common.messaging.events.EventHeaders;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractOutboxMessageProducer implements CommandProducer, DomainEventProducer {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String publish(String channel, String responseChannel, String aggregateId, Command command) {
		String payload = this.encodePayload(command);
		OutboxMessage message = new OutboxMessage(channel, aggregateId, payload);

		String encodedHeaders = this.encodeCommandHeaders(responseChannel, message.getMessageId(), command.getType());
		message.setHeaders(encodedHeaders);

		this.saveAndDelete(message);
		log.info("A command message directed to " + channel + " was saved (payload:  " + payload + ")");

		return message.getMessageId();
	}

	private String encodeCommandHeaders(String responseChannel, String messageId, String type) {
		try {
			Map<String, String> headers = new HashMap<>();
			headers.put(CommandHeaders.RESPONSE_CHANNEL, responseChannel);
			headers.put(CommandHeaders.MESSAGE_ID, messageId);
			headers.put(CommandHeaders.TYPE, type);
			return this.objectMapper.writeValueAsString(headers);
		} catch (Exception ex) {
			log.error("An error occurred during headers encoding: ", ex);
			throw new InternalServerException();
		}
	}

	@Override
	public void publish(String channel, String aggregateId, List<DomainEvent> events) {
		for(DomainEvent event : events) {
			String payload = this.encodePayload(event);
			OutboxMessage message = new OutboxMessage(channel, aggregateId, payload);

			String encodedHeaders = this.encodeEventHeaders(aggregateId, message.getMessageId(), event.getType());
			message.setHeaders(encodedHeaders);

			this.saveAndDelete(message);
			log.info("An event message directed to " + channel + " was saved (payload:  " + payload + ")");
		}
	}

	private String encodeEventHeaders(String aggregateId, String messageId, String type) {
		try {
			Map<String, String> headers = new HashMap<>();
			headers.put(EventHeaders.AGGREGATE_ID, aggregateId);
			headers.put(EventHeaders.MESSAGE_ID, messageId);
			headers.put(EventHeaders.TYPE, type);
			return this.objectMapper.writeValueAsString(headers);
		} catch(Exception ex) {
			log.error("An error occurred during headers encoding: ", ex);
			throw new InternalServerException();
		}
	}

	private String encodePayload(Object message) {
		try {
			return this.objectMapper.writeValueAsString(message);
		} catch (Exception ex) {
			log.error("An error occurred during payload encoding", ex);
			throw new InternalServerException();
		}
	}

	protected abstract void saveAndDelete(OutboxMessage message);
}
