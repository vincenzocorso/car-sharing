package it.vincenzocorso.carsharing.common.saga;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiConsumer;

@AllArgsConstructor
@Getter
public class SagaStepAction<S extends SagaState> {
	private final String actionName;
	private final SagaParticipantEndpoint participantEndpoint;
	private final BiConsumer<SagaParticipantEndpoint, S> action;
}
