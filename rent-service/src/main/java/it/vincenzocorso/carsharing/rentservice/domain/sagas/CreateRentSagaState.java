package it.vincenzocorso.carsharing.rentservice.domain.sagas;

import it.vincenzocorso.carsharing.common.saga.SagaState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class CreateRentSagaState extends SagaState {
	private final String customerId;
	private final String vehicleId;
	private String rentId;
}
