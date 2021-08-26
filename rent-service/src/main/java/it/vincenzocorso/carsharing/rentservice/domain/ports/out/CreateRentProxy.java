package it.vincenzocorso.carsharing.rentservice.domain.ports.out;

import it.vincenzocorso.carsharing.common.saga.SagaParticipantEndpoint;

public interface CreateRentProxy extends SagaParticipantEndpoint {
	void createRent(String customerId, String vehicleId);
}
