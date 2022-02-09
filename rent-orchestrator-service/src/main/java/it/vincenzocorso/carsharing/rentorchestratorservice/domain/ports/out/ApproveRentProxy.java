package it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.out;

import it.vincenzocorso.carsharing.common.saga.SagaParticipantEndpoint;

public interface ApproveRentProxy extends SagaParticipantEndpoint {
	void approveRent(String rentId);
}
