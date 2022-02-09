package it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.out;

import it.vincenzocorso.carsharing.common.saga.Saga;
import it.vincenzocorso.carsharing.common.saga.SagaState;

public interface RentSagaManager {
	void startSaga(Saga saga, SagaState sagaState);
}
