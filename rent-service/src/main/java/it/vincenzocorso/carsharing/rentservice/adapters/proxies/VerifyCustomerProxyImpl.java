package it.vincenzocorso.carsharing.rentservice.adapters.proxies;

import it.vincenzocorso.carsharing.common.saga.SagaState;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.VerifyCustomerProxy;
import it.vincenzocorso.carsharing.rentservice.domain.sagas.CreateRentSagaState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VerifyCustomerProxyImpl implements VerifyCustomerProxy {
	private static final String RESPONSE_MESSAGE_TYPE = "VERIFY_CUSTOMER_COMMAND_RESPONSE";

	@Override
	public void verifyCustomer(String customerId) {
		log.debug("VERIFY_CUSTOMER: " + customerId);
	}

	@Override
	public void invoke(SagaState state) {
		if(state instanceof CreateRentSagaState)
			this.verifyCustomer(((CreateRentSagaState) state).getCustomerId());
	}

	@Override
	public String getName() {
		return "VERIFY_CUSTOMER_ENDPOINT";
	}

	@Override
	public String getResponseMessageType() {
		return RESPONSE_MESSAGE_TYPE;
	}
}
