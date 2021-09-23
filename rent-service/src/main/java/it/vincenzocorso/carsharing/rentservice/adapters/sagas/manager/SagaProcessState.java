package it.vincenzocorso.carsharing.rentservice.adapters.sagas.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.vincenzocorso.carsharing.common.saga.SagaState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import static it.vincenzocorso.carsharing.rentservice.adapters.sagas.manager.SagaProcessVariables.*;

@Component
@Slf4j
@AllArgsConstructor
public class SagaProcessState {
	private final ObjectMapper objectMapper;

	public SagaState getState(DelegateExecution execution) throws Exception {
		String encodedState = (String)execution.getVariable(SAGA_STATE);
		log.debug("encodedState: " + encodedState);

		String sagaStateClassName = (String)execution.getVariable(SAGA_STATE_CLASS_NAME);
		log.debug("sagaStateClassName: " + sagaStateClassName);
		Class<? extends SagaState> stateClass = Class.forName(sagaStateClassName).asSubclass(SagaState.class);

		return this.objectMapper.readValue(encodedState, stateClass);
	}

	public String encodeState(SagaState sagaState) throws Exception {
		return this.objectMapper.writeValueAsString(sagaState);
	}

	public void updateState(DelegateExecution execution, SagaState updatedSagaState) throws Exception {
		execution.setVariable(SAGA_STATE, this.encodeState(updatedSagaState));
	}
}
