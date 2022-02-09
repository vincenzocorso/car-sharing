package it.vincenzocorso.carsharing.rentorchestratorservice.domain.sagas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.vincenzocorso.carsharing.common.saga.SagaState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
public class CreateRentSagaState implements SagaState {
	private final String rentId;
	private final String customerId;
	private final String vehicleId;

	@Override
	@JsonIgnore
	public String getCorrelationId() {
		return this.rentId;
	}
}
