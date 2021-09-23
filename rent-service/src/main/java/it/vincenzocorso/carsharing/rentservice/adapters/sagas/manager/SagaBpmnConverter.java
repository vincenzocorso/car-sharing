package it.vincenzocorso.carsharing.rentservice.adapters.sagas.manager;

import it.vincenzocorso.carsharing.common.saga.Saga;
import it.vincenzocorso.carsharing.common.saga.SagaState;
import it.vincenzocorso.carsharing.common.saga.SagaStep;
import it.vincenzocorso.carsharing.common.saga.SagaStepAction;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.SendTaskBuilder;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.springframework.stereotype.Component;

import java.util.List;

import static it.vincenzocorso.carsharing.rentservice.adapters.sagas.manager.SagaProcessVariables.SAGA_BACKWARD_ACTION_FLAG;
import static it.vincenzocorso.carsharing.rentservice.adapters.sagas.manager.SagaProcessVariables.SAGA_STEP_NUMBER;

@Component
public class SagaBpmnConverter {
	public <S extends SagaState> BpmnModelInstance convert(Saga<S> saga) {
		ProcessBuilder processBuilder = Bpmn.createExecutableProcess(saga.getName()).name(saga.getName());

		this.buildSteps(processBuilder, saga.getDefinition().getSteps());
		this.buildExceptionHandler(processBuilder);

		return processBuilder.done();
	}

	private <S extends SagaState> void buildSteps(ProcessBuilder processBuilder, List<SagaStep<S>> steps) {
		FlowNode lastNode = processBuilder.startEvent().getElement();
		for(int stepNumber = 0; stepNumber < steps.size(); stepNumber++)
			lastNode = this.buildStep(lastNode, steps.get(stepNumber), stepNumber);
		lastNode.builder().endEvent();
	}

	private <S extends SagaState> FlowNode buildStep(FlowNode lastNode, SagaStep<S> step, int stepNumber) {
		SagaStepAction<S> forwardAction = step.getForwardAction();
		SagaStepAction<S> backwardAction = step.getBackwardAction();
		SendTaskBuilder stepBuilder = lastNode.builder().sendTask();

		FlowNode currentNode;
		if(forwardAction != null) {
			currentNode = stepBuilder
					.name(forwardAction.getActionName())
					.camundaInputParameter(SAGA_STEP_NUMBER, Integer.toString(stepNumber))
					.camundaClass(SagaStepExecutor.class)
					.getElement();
		} else {
			currentNode = stepBuilder
					.camundaClass(EmptyStepExecutor.class)
					.getElement();
		}

		if(backwardAction != null) {
			stepBuilder.boundaryEvent().compensateEventDefinition().compensateEventDefinitionDone()
					.compensationStart()
					.sendTask()
						.name(backwardAction.getActionName())
						.camundaInputParameter(SAGA_STEP_NUMBER, Integer.toString(stepNumber))
						.camundaInputParameter(SAGA_BACKWARD_ACTION_FLAG, "true")
						.camundaClass(SagaStepExecutor.class)
					.receiveTask()
						.name("Receiving " + backwardAction.getParticipantEndpoint().getResponseMessageType() + " message")
						.message(backwardAction.getParticipantEndpoint().getResponseMessageType())
					.compensationDone();
		}

		if(forwardAction != null) {
			currentNode = stepBuilder
					.receiveTask()
					.name("Receiving " + forwardAction.getParticipantEndpoint().getResponseMessageType() + " message")
					.message(forwardAction.getParticipantEndpoint().getResponseMessageType())
					.getElement();
		}

		return currentNode;
	}

	private void buildExceptionHandler(ProcessBuilder processBuilder) {
		processBuilder.eventSubProcess()
				.startEvent().error("java.lang.Throwable")
				.intermediateThrowEvent().compensateEventDefinition().compensateEventDefinitionDone()
				.endEvent();
	}
}
