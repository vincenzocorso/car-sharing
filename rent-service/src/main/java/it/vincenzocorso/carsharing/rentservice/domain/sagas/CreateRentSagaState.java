package it.vincenzocorso.carsharing.rentservice.domain.sagas;

import it.vincenzocorso.carsharing.common.saga.SagaState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class CreateRentSagaState implements SagaState {
	private final String rentId;
	private final String customerId;
	private final String vehicleId;

	@Override
	public String getCorrelationId() {
		return this.rentId;
	}
}
