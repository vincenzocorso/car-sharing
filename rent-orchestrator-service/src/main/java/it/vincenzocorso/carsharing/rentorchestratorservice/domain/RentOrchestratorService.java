package it.vincenzocorso.carsharing.rentorchestratorservice.domain;

import it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.in.RentSagaOrchestrator;
import it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.out.RentSagaManager;
import it.vincenzocorso.carsharing.rentorchestratorservice.domain.sagas.CreateRentSaga;
import it.vincenzocorso.carsharing.rentorchestratorservice.domain.sagas.CreateRentSagaState;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentOrchestratorService implements RentSagaOrchestrator {
	private final RentSagaManager rentSagaManager;
	private final CreateRentSaga createRentSaga;

	@Override
	public void startCreateRentSaga(String rentId, String customerId, String vehicleId) {
		CreateRentSagaState state = new CreateRentSagaState(rentId, customerId, vehicleId);
		this.rentSagaManager.startSaga(this.createRentSaga, state);
	}
}
