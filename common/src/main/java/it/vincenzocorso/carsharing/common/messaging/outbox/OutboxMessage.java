package it.vincenzocorso.carsharing.common.messaging.outbox;

public interface OutboxMessage {
	String MESSAGE_ID = "message_id";
	String CHANNEL = "channel";
	String MESSAGE_KEY = "message_key";
	String PAYLOAD = "payload";
	String HEADERS = "headers";

	String getMessageId();
	String getChannel();
	String getMessageKey();
	String getPayload();
	String getHeaders();
}
