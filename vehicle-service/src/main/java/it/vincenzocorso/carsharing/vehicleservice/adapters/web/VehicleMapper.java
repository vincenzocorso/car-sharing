package it.vincenzocorso.carsharing.vehicleservice.adapters.web;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.Vehicle;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VehicleMapper {
    public VehicleResponse convertToDto(Vehicle vehicle) {
        VehiclePosition position = new VehiclePosition(vehicle.getStatus().latitude(), vehicle.getStatus().longitude());

        return VehicleResponse.builder()
                .vehicleId(vehicle.getId())
                .licensePlate(vehicle.getLicensePlate())
                .position(position)
                .autonomy(vehicle.getStatus().autonomy())
                .currentState(vehicle.getCurrentState().toString())
                .vehicleModelId(vehicle.getVehicleModelId())
                .build();
    }
}
