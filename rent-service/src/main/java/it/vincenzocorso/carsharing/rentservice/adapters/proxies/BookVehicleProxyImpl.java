package it.vincenzocorso.carsharing.rentservice.adapters.proxies;

import it.vincenzocorso.carsharing.common.saga.SagaState;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.BookVehicleProxy;
import it.vincenzocorso.carsharing.rentservice.domain.sagas.CreateRentSagaState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookVehicleProxyImpl implements BookVehicleProxy {
	private static final String RESPONSE_MESSAGE_TYPE = "BOOK_VEHICLE_COMMAND_RESPONSE";

	@Override
	public void bookVehicle(String vehicleId) {
		log.debug("BOOK_VEHICLE: " + vehicleId);
	}

	@Override
	public void invoke(SagaState state) {
		if(state instanceof CreateRentSagaState)
			this.bookVehicle(((CreateRentSagaState) state).getVehicleId());
	}

	@Override
	public String getName() {
		return "BOOK_VEHICLE_ENDPOINT";
	}

	@Override
	public String getResponseMessageType() {
		return RESPONSE_MESSAGE_TYPE;
	}
}
