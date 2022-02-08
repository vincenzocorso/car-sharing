package it.vincenzocorso.carsharing.rentservice.adapters.proxies;

import it.vincenzocorso.carsharing.common.saga.SagaState;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.ApproveRentProxy;
import it.vincenzocorso.carsharing.rentservice.domain.sagas.CreateRentSagaState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApproveRentProxyImpl implements ApproveRentProxy {
	@Override
	public void approveRent(String rentId) {
		log.debug("APPROVE_RENT: " + rentId);
	}

	@Override
	public void invoke(SagaState state) {
		if(state instanceof CreateRentSagaState)
			this.approveRent(((CreateRentSagaState) state).getRentId());
	}

	@Override
	public String getName() {
		return "APPROVE_RENT_ENDPOINT";
	}

	@Override
	public String getResponseMessageType() {
		return null;
	}
}
