package it.vincenzocorso.carsharing.rentservice.adapters.sagas.proxies;

import it.vincenzocorso.carsharing.rentservice.domain.ports.in.RentVehicleUseCase;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.RejectRentProxy;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RejectRentProxyImpl implements RejectRentProxy {
	private final RentVehicleUseCase rentVehicleUseCase;
	private final ProcessEngine camunda;

	@Override
	public String getMessageType() {
		return "REJECT_RENT_COMMAND";
	}

	@Override
	public String getResponseMessageType() {
		return "REJECT_RENT_REPLY";
	}

	@Override
	public void rejectRent(String rentId) {
		this.rentVehicleUseCase.rejectRent(rentId);

		this.camunda.getRuntimeService().createMessageCorrelation(this.getResponseMessageType())
				.processInstanceBusinessKey(rentId)
				.correlateWithResult();
	}
}
