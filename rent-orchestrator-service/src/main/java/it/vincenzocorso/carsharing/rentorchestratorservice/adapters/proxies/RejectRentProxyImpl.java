package it.vincenzocorso.carsharing.rentorchestratorservice.adapters.proxies;

import it.vincenzocorso.carsharing.common.saga.SagaState;
import it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.out.RejectRentProxy;
import it.vincenzocorso.carsharing.rentorchestratorservice.domain.sagas.CreateRentSagaState;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Slf4j
public class RejectRentProxyImpl implements RejectRentProxy {
	@Override
	public void rejectRent(String rentId) {
		log.debug("REJECT_RENT: " + rentId);
	}

	@Override
	public void invoke(SagaState state) {
		if(state instanceof CreateRentSagaState)
			this.rejectRent(((CreateRentSagaState) state).getRentId());
	}

	@Override
	public String getName() {
		return "REJECT_RENT_ENDPOINT";
	}

	@Override
	public String getResponseMessageType() {
		return null;
	}
}
