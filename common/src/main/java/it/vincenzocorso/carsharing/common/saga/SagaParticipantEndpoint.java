package it.vincenzocorso.carsharing.common.saga;

public interface SagaParticipantEndpoint {
	void invoke(SagaState state);
	String getName();
	String getResponseMessageType();
}
