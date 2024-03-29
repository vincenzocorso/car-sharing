package it.vincenzocorso.carsharing.common.spring.messaging.outbox.jpa;

import it.vincenzocorso.carsharing.common.messaging.outbox.AbstractOutboxMessageProducer;
import it.vincenzocorso.carsharing.common.messaging.outbox.OutboxMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OutboxJpaMessageProducer extends AbstractOutboxMessageProducer {
	private final OutboxJpaMessageRepository messageRepository;

	@Override
	protected void saveAndDelete(OutboxMessage message) {
		OutboxMessageEntity messageEntity = this.toEntity(message);
		messageEntity = this.messageRepository.save(messageEntity);
		this.messageRepository.delete(messageEntity);
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
