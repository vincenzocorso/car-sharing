package it.vincenzocorso.carsharing.common.quarkus.messaging.outbox.mongodb;

import it.vincenzocorso.carsharing.common.messaging.outbox.AbstractOutboxMessageProducer;
import it.vincenzocorso.carsharing.common.messaging.outbox.OutboxMessage;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OutboxMongoMessageProducer extends AbstractOutboxMessageProducer {
	@Override
	protected void saveAndDelete(OutboxMessage message) {
		OutboxMessageEntity messageEntity = this.toEntity(message);
		messageEntity.persist();
		OutboxMessageEntity.deleteById(message.getMessageId());
	}

	private OutboxMessageEntity toEntity(OutboxMessage message) {
		OutboxMessageEntity messageEntity = new OutboxMessageEntity();
		messageEntity.messageId = message.getMessageId();
		messageEntity.channel = message.getChannel();
		messageEntity.messageKey = message.getMessageKey();
		messageEntity.payload = message.getPayload();
		messageEntity.headers = message.getHeaders();
		return messageEntity;
	}
}
