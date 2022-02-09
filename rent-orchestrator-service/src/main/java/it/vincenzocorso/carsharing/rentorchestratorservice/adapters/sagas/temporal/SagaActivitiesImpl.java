package it.vincenzocorso.carsharing.rentorchestratorservice.adapters.sagas.temporal;

import it.vincenzocorso.carsharing.common.saga.SagaState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Slf4j
@AllArgsConstructor
public class SagaActivitiesImpl implements SagaActivities {
	private final SagaParticipantRepository sagaParticipantRepository;

	@Override
	public void invokeParticipant(String participantName, SagaState state) {
		this.sagaParticipantRepository.getParticipant(participantName).invoke(state);
	}
}