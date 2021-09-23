package it.vincenzocorso.carsharing.rentservice.adapters.sagas.proxies;

import it.vincenzocorso.carsharing.common.messaging.commands.CommandProducer;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.VerifyCustomerProxy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VerifyCustomerProxyImpl implements VerifyCustomerProxy {
	private final CommandProducer commandProducer;

	@Override
	public String getMessageType() {
		// TODO: extract
		return "VERIFY_CUSTOMER_COMMAND";
	}

	@Override
	public String getResponseMessageType() {
		// TODO: extract
		return "VERIFY_CUSTOMER_REPLY";
	}

	@Override
	public void verifyCustomer(String customerId) {
	}
}
