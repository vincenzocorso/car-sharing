package it.vincenzocorso.carsharing.common.quarkus.messaging.outbox.jpa;

import it.vincenzocorso.carsharing.common.messaging.outbox.AbstractOutboxMessageProducer;
import it.vincenzocorso.carsharing.common.messaging.outbox.OutboxMessage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class OutboxJpaMessageProducer extends AbstractOutboxMessageProducer {
	@Inject
	EntityManager entityManager;

	@Override
	protected void saveAndDelete(OutboxMessage message) {
		OutboxMessageEntity messageEntity = this.toEntity(message);
		this.entityManager.persist(messageEntity);
		this.entityManager.remove(messageEntity);
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
