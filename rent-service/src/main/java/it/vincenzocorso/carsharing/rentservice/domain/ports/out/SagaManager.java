package it.vincenzocorso.carsharing.rentservice.domain.ports.out;

import it.vincenzocorso.carsharing.common.saga.Saga;
import it.vincenzocorso.carsharing.common.saga.SagaState;

public interface SagaManager {
	<S extends SagaState> void startSaga(Saga<S> saga, S sagaState);
}
