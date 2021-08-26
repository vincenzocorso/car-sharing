package it.vincenzocorso.carsharing.common.saga;

public interface SagaParticipantEndpoint {
	String getMessageType();
	String getResponseMessageType();
}
