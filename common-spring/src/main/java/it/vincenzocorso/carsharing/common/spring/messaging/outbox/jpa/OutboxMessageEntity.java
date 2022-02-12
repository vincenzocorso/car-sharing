package it.vincenzocorso.carsharing.common.spring.messaging.outbox.jpa;

import it.vincenzocorso.carsharing.common.messaging.MessageFields;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "outbox")
@NoArgsConstructor
@Getter
@Setter
public class OutboxMessageEntity {
	@Id
	@Column(name = MessageFields.MESSAGE_ID)
	private String messageId;

	@Column(name = MessageFields.CHANNEL)
	private String channel;

	@Column(name = MessageFields.MESSAGE_KEY)
	private String messageKey;

	@Column(name = MessageFields.PAYLOAD)
	private String payload;

	@Column(name = MessageFields.HEADERS)
	private String headers;

	public OutboxMessageEntity(String channel, String messageKey, String payload) {
		this.messageId = UUID.randomUUID().toString();
		this.channel = channel;
		this.messageKey = messageKey;
		this.payload = payload;
	}
}
