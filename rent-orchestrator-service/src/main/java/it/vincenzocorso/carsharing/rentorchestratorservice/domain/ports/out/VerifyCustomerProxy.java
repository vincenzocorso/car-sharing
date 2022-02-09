package it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.out;

import it.vincenzocorso.carsharing.common.saga.SagaParticipantEndpoint;

public interface VerifyCustomerProxy extends SagaParticipantEndpoint {
	void verifyCustomer(String customerId);
}
