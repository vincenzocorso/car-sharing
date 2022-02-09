package it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.in;

public interface RentSagaOrchestrator {
	void startCreateRentSaga(String rentId, String customerId, String vehicleId);
}
