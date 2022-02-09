package it.vincenzocorso.carsharing.rentorchestratorservice.adapters.sagas.temporal;

import it.vincenzocorso.carsharing.common.saga.SagaParticipantEndpoint;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
@Slf4j
public class SagaParticipantRepository {
	private final Map<String, SagaParticipantEndpoint> participantsMap = new HashMap<>();

	public SagaParticipantRepository(@Any Instance<SagaParticipantEndpoint> participants) {
		participants.forEach(participant -> this.participantsMap.put(participant.getName(), participant));
	}

	public SagaParticipantEndpoint getParticipant(String participantName) {
		SagaParticipantEndpoint participant = this.participantsMap.get(participantName);

		if(participant == null) {
			String message = "Saga participant not found: " + participantName;
			log.error(message);
			throw new IllegalArgumentException(message);
		}

		return participant;
	}
}
