package it.vincenzocorso.carsharing.vehicleservice.domain.ports.in;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.Vehicle;

public interface RentVehicleUseCase {
    /**
     * Books a vehicle
     * @param vehicleId The id of the vehicle to book
     * @throws it.vincenzocorso.carsharing.vehicleservice.domain.exceptions.VehicleNotFoundException If the vehicle with the given id does not exist
     * @throws it.vincenzocorso.carsharing.vehicleservice.domain.exceptions.IllegalVehicleStateTransitionException If the vehicle cannot be booked
     */
    Vehicle bookVehicle(String vehicleId);
}
