package it.vincenzocorso.carsharing.common.messaging.commands;

public interface CommandProducer {
	String publish(String channel, String responseChannel, String aggregateId, Command command);
}
