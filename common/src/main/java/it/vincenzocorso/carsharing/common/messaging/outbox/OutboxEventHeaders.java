package it.vincenzocorso.carsharing.common.messaging.outbox;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OutboxEventHeaders {
	public static final String AGGREGATE_ID = "aggregate_id";
	public static final String MESSAGE_ID = "message_id";
	public static final String TYPE = "type";
}
