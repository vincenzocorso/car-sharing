package it.vincenzocorso.carsharing.rentservice.adapters.sagas.manager;

import it.vincenzocorso.carsharing.common.saga.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static it.vincenzocorso.carsharing.rentservice.adapters.sagas.manager.SagaProcessVariables.*;

@Component
@Slf4j
@AllArgsConstructor
public class SagaStepExecutor implements JavaDelegate {
	private final ApplicationContext applicationContext;
	private final SagaProcessState sagaProcessState;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		SagaState sagaState = this.sagaProcessState.getState(execution);
		this.executeSagaStep(execution, sagaState);
		this.sagaProcessState.updateState(execution, sagaState);
	}

	private void executeSagaStep(DelegateExecution execution, SagaState sagaState) throws ClassNotFoundException{
		SagaStep<SagaState> sagaStep = this.getSagaStep(execution);
		boolean isBackwardAction = execution.hasVariable(SAGA_BACKWARD_ACTION_FLAG);
		SagaStepAction<SagaState> stepAction = (isBackwardAction) ? sagaStep.getBackwardAction() : sagaStep.getForwardAction();
		stepAction.getAction().accept(stepAction.getParticipantEndpoint(), sagaState);
		log.debug("Step " + stepAction.getActionName() + " executed");
	}

	private SagaStep<SagaState> getSagaStep(DelegateExecution execution) throws ClassNotFoundException {
		String stepNumber = (String)execution.getVariable(SAGA_STEP_NUMBER);
		String sagaClassName = (String)execution.getVariable(SAGA_CLASS_NAME);
		Saga<SagaState> saga = this.applicationContext.getBean(Class.forName(sagaClassName).asSubclass(Saga.class));
		return saga.getDefinition().getSteps().get(Integer.parseInt(stepNumber));
	}
}

