package it.vincenzocorso.carsharing.rentorchestratorservice.domain.sagas;

import it.vincenzocorso.carsharing.common.saga.Saga;
import it.vincenzocorso.carsharing.common.saga.SagaDefinition;
import it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.out.ApproveRentProxy;
import it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.out.BookVehicleProxy;
import it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.out.RejectRentProxy;
import it.vincenzocorso.carsharing.rentorchestratorservice.domain.ports.out.VerifyCustomerProxy;
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