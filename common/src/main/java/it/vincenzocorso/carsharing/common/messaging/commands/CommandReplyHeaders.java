package it.vincenzocorso.carsharing.common.messaging.commands;

public interface CommandReplyHeaders {
    String CORRELATION_ID = "correlation_id";
    String MESSAGE_ID = "message_id";
    String TYPE = "type";
}
