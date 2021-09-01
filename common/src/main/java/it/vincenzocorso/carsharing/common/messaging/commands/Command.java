package it.vincenzocorso.carsharing.common.messaging.commands;

public interface Command {
	String getResponseChannel();
	String getType();
}
