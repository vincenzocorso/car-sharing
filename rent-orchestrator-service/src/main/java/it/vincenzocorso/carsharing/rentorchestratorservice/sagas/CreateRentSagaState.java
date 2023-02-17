package it.vincenzocorso.carsharing.rentorchestratorservice.sagas;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class CreateRentSagaState {
    public String rentId;
    public String customerId;
    public String vehicleId;

    public Boolean canCustomerRent;
    public String rejectReason;

    public CreateRentSagaState(String rentId, String customerId, String vehicleId) {
        this.rentId = rentId;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
    }
}
