package it.vincenzocorso.carsharing.rentorchestratorservice.adapters.sagas.temporal;

import io.temporal.activity.ActivityInterface;
import it.vincenzocorso.carsharing.common.saga.SagaState;

@ActivityInterface
public interface SagaActivities {
	void invokeParticipant(String participantName, SagaState state);
}
