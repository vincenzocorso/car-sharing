package it.vincenzocorso.carsharing.common.messaging.commands;

public interface CommandProducer {
	void publish(String channel, String aggregateId, Command command);
}
