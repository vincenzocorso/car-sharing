package it.vincenzocorso.carsharing.common.messaging.outbox;

public interface OutboxMessage {
	String MESSAGE_ID_FIELD_NAME = "message_id";
	String CHANNEL_FIELD_NAME = "channel";
	String MESSAGE_KEY_FIELD_NAME = "message_key";
	String PAYLOAD_FIELD_NAME = "payload";
	String HEADERS_FIELD_NAME = "headers";

	String getMessageId();
	String getChannel();
	String getMessageKey();
	String getPayload();
	String getHeaders();
}
