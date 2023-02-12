package it.vincenzocorso.carsharing.common.quarkus.messaging.outbox.jpa;

import it.vincenzocorso.carsharing.common.messaging.MessageFields;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "outbox")
@NoArgsConstructor
public class OutboxMessageEntity {
	@Id
	@Column(name = MessageFields.MESSAGE_ID)
	public String messageId;

	@Column(name = MessageFields.CHANNEL)
	public String channel;

	@Column(name = MessageFields.MESSAGE_KEY)
	public String messageKey;

	@Column(name = MessageFields.PAYLOAD)
	public String payload;

	@Column(name = MessageFields.HEADERS)
	public String headers;
}
