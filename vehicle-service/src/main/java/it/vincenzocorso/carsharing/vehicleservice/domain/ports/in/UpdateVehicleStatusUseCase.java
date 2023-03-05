package it.vincenzocorso.carsharing.vehicleservice.domain.ports.in;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.VehicleStatus;

public interface UpdateVehicleStatusUseCase {
    void updateVehicleStatus(String vehicleId, VehicleStatus newStatus);
}
