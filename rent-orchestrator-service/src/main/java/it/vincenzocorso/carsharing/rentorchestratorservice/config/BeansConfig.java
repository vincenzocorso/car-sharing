package it.vincenzocorso.carsharing.rentorchestratorservice.config;

import it.vincenzocorso.carsharing.rentorchestratorservice.domain.RentOrchestratorService;
import it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.in.RentSagaOrchestrator;
import it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.out.*;
import it.vincenzocorso.carsharing.rentorchestratorservice.domain.sagas.CreateRentSaga;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Produces;

public class BeansConfig {
	@Produces @ApplicationScoped
	CreateRentSaga createRentSaga(
			RejectRentProxy rejectRentProxy,
			VerifyCustomerProxy verifyCustomerProxy,
			BookVehicleProxy bookVehicleProxy,
			ApproveRentProxy approveRentProxy) {
		return new CreateRentSaga(rejectRentProxy, verifyCustomerProxy, bookVehicleProxy, approveRentProxy);
	}

	@Produces @ApplicationScoped
	RentSagaOrchestrator rentSagaOrchestrator(RentSagaManager rentSagaManager, CreateRentSaga createRentSaga) {
		return new RentOrchestratorService(rentSagaManager, createRentSaga);
	}
}
