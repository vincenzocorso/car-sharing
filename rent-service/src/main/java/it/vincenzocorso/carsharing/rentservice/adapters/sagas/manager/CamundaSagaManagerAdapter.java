package it.vincenzocorso.carsharing.rentservice.adapters.sagas.manager;

import it.vincenzocorso.carsharing.common.saga.Saga;
import it.vincenzocorso.carsharing.common.saga.SagaState;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.SagaManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.stereotype.Component;

import static it.vincenzocorso.carsharing.rentservice.adapters.sagas.manager.SagaProcessVariables.*;

@Component
@Slf4j
@AllArgsConstructor
public class CamundaSagaManagerAdapter implements SagaManager {
	private final SagaBpmnConverter sagaBpmnConverter;
	private final SagaProcessState sagaProcessState;
	private final ProcessEngine camunda;

	public <S extends SagaState> void registerSaga(Saga<S> saga) {
		BpmnModelInstance bpmnSagaModel = this.sagaBpmnConverter.convert(saga);
		this.deployBpmnModel(saga.getName(), bpmnSagaModel);
	}

	private void deployBpmnModel(String modelName, BpmnModelInstance modelInstance) {
		String modelInstanceName = modelName + ".bpmn";
		this.camunda.getRepositoryService()
				.createDeployment()
				.addModelInstance(modelInstanceName, modelInstance)
				.deploy();
		log.info("Process model " + modelInstanceName + " deployed");
	}

	@Override
	public <S extends SagaState> void startSaga(Saga<S> saga, S sagaState) {
		try {
			VariableMap variables = Variables
					.putValue(SAGA_CLASS_NAME, saga.getClass().getName())
					.putValue(SAGA_STATE_CLASS_NAME, sagaState.getClass().getName())
					.putValue(SAGA_STATE, this.sagaProcessState.encodeState(sagaState));
			this.camunda.getRuntimeService().startProcessInstanceByKey(saga.getName(), sagaState.getCorrelationId(), variables);
			log.info("Saga " + saga.getName() + " started with id " + sagaState.getCorrelationId());
		} catch (Exception ex) {
			log.error(ex.toString());
		}
	}
}
