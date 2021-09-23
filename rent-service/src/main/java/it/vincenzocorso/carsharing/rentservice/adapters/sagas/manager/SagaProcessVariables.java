package it.vincenzocorso.carsharing.rentservice.adapters.sagas.manager;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SagaProcessVariables {
	public static final String SAGA_STATE_CLASS_NAME = "sagaStateClassName";
	public static final String SAGA_STATE = "sagaState";
	public static final String SAGA_STEP_NUMBER = "sagaStepNumber";
	public static final String SAGA_CLASS_NAME = "sagaClassName";
	public static final String SAGA_BACKWARD_ACTION_FLAG = "sagaBackwardActionFlag";
}
