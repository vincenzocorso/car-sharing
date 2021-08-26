package it.vincenzocorso.carsharing.rentservice.domain.sagas;

import it.vincenzocorso.carsharing.common.saga.Saga;
import it.vincenzocorso.carsharing.common.saga.SagaDefinition;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.*;
import lombok.AllArgsConstructor;

import static it.vincenzocorso.carsharing.common.saga.SagaDefinitionBuilder.*;

@AllArgsConstructor
public class CreateRentSaga implements Saga<CreateRentSagaState> {
	private final CreateRentProxy createRentProxy;
	private final RejectRentProxy rejectRentProxy;
	private final VerifyCustomerProxy verifyCustomerProxy;
	private final BookVehicleProxy bookVehicleProxy;
	private final ApproveRentProxy approveRentProxy;

	@Override
	public String getName() {
		return "CREATE_RENT_SAGA";
	}

	@Override
	public SagaDefinition<CreateRentSagaState> getDefinition() {
		return start(CreateRentSagaState.class)
				.step()
					.forward("Creating Rent", this.createRentProxy, (participant, state) -> participant.createRent(state.getCustomerId(), state.getVehicleId()))
					.backward("Rejecting Rent", this.rejectRentProxy, (participant, state) -> participant.rejectRent(state.getRentId()))
				.step()
					.forward("Verifying Customer", this.verifyCustomerProxy, (participant, state) -> participant.verifyCustomer(state.getCustomerId()))
				.step()
					.forward("Booking Vehicle", this.bookVehicleProxy, (participant, state) -> participant.bookVehicle(state.getVehicleId()))
				.retriableSteps()
					.forward("Approving Rent", this.approveRentProxy, (participant, state) -> participant.approveRent(state.getRentId()))
				.end();
	}
}
