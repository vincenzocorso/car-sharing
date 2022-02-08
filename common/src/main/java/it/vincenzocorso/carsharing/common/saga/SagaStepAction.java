package it.vincenzocorso.carsharing.common.saga;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SagaStepAction {
	private final String actionName;
	private final SagaParticipantEndpoint participantEndpoint;
}
