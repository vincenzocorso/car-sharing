package it.vincenzocorso.carsharing.vehicleservice.adapters.web;

import it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicleModel;

import static it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicle.*;

public class FakeVehicleDto {
    public static final VehicleResponse VEHICLE_RESPONSE = VehicleResponse.builder()
            .vehicleId(VEHICLE_ID)
            .licensePlate(LICENSE_PLATE)
            .position(new VehiclePosition(LATITUDE, LONGITUDE))
            .autonomy(AUTONOMY)
            .currentState(VEHICLE_STATE.toString())
            .vehicleModelId(FakeVehicleModel.VEHICLE_MODEL_ID)
            .build();
}
