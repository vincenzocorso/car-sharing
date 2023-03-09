package it.vincenzocorso.carsharing.rentorchestratorservice.sagas;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface CreateRentSagaActivities {
    void verifyCustomer(String customerId);
    void bookVehicle(String vehicleId);
    void rejectRent(String rentId);
}
