package it.vincenzocorso.carsharing.rentservice.domain.sagas;

import it.vincenzocorso.carsharing.common.saga.Saga;
import it.vincenzocorso.carsharing.common.saga.SagaDefinition;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.*;
import lombok.AllArgsConstructor;

import static it.vincenzocorso.carsharing.common.saga.SagaDefinitionBuilder.*;

@AllArgsConstructor
public class CreateRentSaga implements Saga {
	private final RejectRentProxy rejectRentProxy;
	private final VerifyCustomerProxy verifyCustomerProxy;
	private final BookVehicleProxy bookVehicleProxy;
	private final ApproveRentProxy approveRentProxy;

	@Override
	public String getName() {
		return "CREATE_RENT_SAGA";
	}

	@Override
	public SagaDefinition getDefinition() {
		return start()
				.step()
					.backward("Rejecting Rent", this.rejectRentProxy)
				.step()
					.forward("Verifying Customer", this.verifyCustomerProxy)
				.step()
					.forward("Booking Vehicle", this.bookVehicleProxy)
				.retriableSteps()
					.forward("Approving Rent", this.approveRentProxy)
				.end();
	}
}
