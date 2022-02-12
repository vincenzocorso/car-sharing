package it.vincenzocorso.carsharing.common.messaging;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageFields {
	public static final String MESSAGE_ID = "message_id";
	public static final String CHANNEL = "channel";
	public static final String MESSAGE_KEY = "message_key";
	public static final String PAYLOAD = "payload";
	public static final String HEADERS = "headers";
}
