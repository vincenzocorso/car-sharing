package it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.out;

import it.vincenzocorso.carsharing.common.saga.SagaParticipantEndpoint;

public interface BookVehicleProxy extends SagaParticipantEndpoint {
	void bookVehicle(String vehicleId);
}
