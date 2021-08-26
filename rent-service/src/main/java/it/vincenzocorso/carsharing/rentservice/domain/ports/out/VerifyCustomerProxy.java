package it.vincenzocorso.carsharing.rentservice.domain.ports.out;

import it.vincenzocorso.carsharing.common.saga.SagaParticipantEndpoint;

public interface VerifyCustomerProxy extends SagaParticipantEndpoint {
	void verifyCustomer(String customerId);
}
