package it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.out;

import it.vincenzocorso.carsharing.common.saga.SagaParticipantEndpoint;

public interface RejectRentProxy extends SagaParticipantEndpoint {
	void rejectRent(String rentId);
}
