package it.vincenzocorso.carsharing.rentservice.adapters.sagas.temporal;

import it.vincenzocorso.carsharing.common.saga.SagaParticipantEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class SagaParticipantRepository {
	private final Map<String, SagaParticipantEndpoint> participantsMap = new HashMap<>();

	@Autowired
	public SagaParticipantRepository(List<SagaParticipantEndpoint> participants) {
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
