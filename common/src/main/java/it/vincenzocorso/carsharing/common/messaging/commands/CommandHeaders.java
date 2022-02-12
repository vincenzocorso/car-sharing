package it.vincenzocorso.carsharing.common.messaging.commands;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandHeaders {
	public static final String RESPONSE_CHANNEL = "response_channel";
	public static final String MESSAGE_ID = "message_id";
	public static final String TYPE = "type";
}
