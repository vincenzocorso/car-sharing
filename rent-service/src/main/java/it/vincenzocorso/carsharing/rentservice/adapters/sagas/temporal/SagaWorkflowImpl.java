package it.vincenzocorso.carsharing.rentservice.adapters.sagas.temporal;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.failure.ActivityFailure;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;
import it.vincenzocorso.carsharing.common.saga.SagaParticipantEndpoint;
import it.vincenzocorso.carsharing.common.saga.SagaState;
import it.vincenzocorso.carsharing.common.saga.SagaStep;
import it.vincenzocorso.carsharing.common.saga.SagaStepAction;
import it.vincenzocorso.carsharing.rentservice.config.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class SagaWorkflowImpl implements SagaWorkflow {
	private final SagaRepository sagas = ApplicationContextUtils.getApplicationContext().getBean(SagaRepository.class);
	private final ActivityOptions options = ActivityOptions.newBuilder()
			.setStartToCloseTimeout(Duration.ofHours(1))
			.setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(1).build())
			.build();
	private final SagaActivities activities = Workflow.newActivityStub(SagaActivities.class, this.options);
	private final Map<String, Boolean> responseArrived = new HashMap<>();
	private boolean needCompensation;

	@Override
	public void startSaga(String sagaName, SagaState state) {
		Saga.Options sagaOptions = new Saga.Options.Builder().setParallelCompensation(true).build();
		Saga workflow = new Saga(sagaOptions);
		try {
			List<SagaStep> steps = this.sagas.getSaga(sagaName).getDefinition().getSteps();
			this.setup(steps);
			for(SagaStep step : steps)
				this.executeStep(workflow, step, state);
		} catch (ActivityFailure e) {
			workflow.compensate();
			throw e;
		}
	}

	private void setup(List<SagaStep> steps) {
		steps.stream()
				.map(SagaStep::getForwardAction)
				.filter(Objects::nonNull)
				.map(SagaStepAction::getParticipantEndpoint)
				.map(SagaParticipantEndpoint::getResponseMessageType)
				.filter(Objects::nonNull)
				.forEach(messageName -> this.responseArrived.put(messageName, false));
	}

	private void executeStep(Saga workflow, SagaStep step, SagaState state) {
		if(step.getForwardAction() != null) {
			SagaParticipantEndpoint participant = step.getForwardAction().getParticipantEndpoint();
			this.activities.invokeParticipant(participant.getName(), state);
			this.waitForResponse(workflow, participant);
		}

		if(step.getBackwardAction() != null) {
			SagaParticipantEndpoint participant = step.getBackwardAction().getParticipantEndpoint();
			workflow.addCompensation(this.activities::invokeParticipant, participant.getName(), state);
		}
	}

	private void waitForResponse(Saga workflow, SagaParticipantEndpoint participant) {
		if(participant.getResponseMessageType() != null) {
			Workflow.await(() -> this.responseArrived.get(participant.getResponseMessageType()));
			if(this.needCompensation)
				workflow.compensate();
		}
	}

	@Override
	public void notifyMessageResponse(String messageName, boolean compensation) {
		this.needCompensation = compensation;
		this.responseArrived.put(messageName, true);
	}
}
