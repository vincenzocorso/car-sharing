package it.vincenzocorso.carsharing.common.messaging.outbox;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OutboxCommandHeaders {
	public static final String RESPONSE_CHANNEL = "response_channel";
	public static final String MESSAGE_ID = "message_id";
	public static final String TYPE = "type";
}
