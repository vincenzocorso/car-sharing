package it.vincenzocorso.carsharing.common.messaging.outbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class OutboxMessage {
	private String messageId;
	private String channel;
	private String messageKey;
	private String payload;
	private String headers;

	public OutboxMessage(String channel, String messageKey, String payload) {
		this.messageId = UUID.randomUUID().toString();
		this.channel = channel;
		this.messageKey = messageKey;
		this.payload = payload;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}
}
