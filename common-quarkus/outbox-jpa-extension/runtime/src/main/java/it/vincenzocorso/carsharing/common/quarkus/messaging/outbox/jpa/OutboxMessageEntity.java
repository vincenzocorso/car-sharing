package it.vincenzocorso.carsharing.common.quarkus.messaging.outbox.jpa;

import it.vincenzocorso.carsharing.common.messaging.MessageFields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

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
