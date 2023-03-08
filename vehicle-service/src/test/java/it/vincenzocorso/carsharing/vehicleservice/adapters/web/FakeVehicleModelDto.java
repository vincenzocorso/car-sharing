package it.vincenzocorso.carsharing.vehicleservice.adapters.web;

import it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicleModel;

public class FakeVehicleModelDto {
    public static final RateTableResponse RATE_TABLE_RESPONSE = RateTableResponse.builder()
            .ratePerMinute(FakeVehicleModel.RATE_PER_MINUTE)
            .ratePerHour(FakeVehicleModel.RATE_PER_HOUR)
            .ratePerDay(FakeVehicleModel.RATE_PER_DAY)
            .ratePerKilometer(FakeVehicleModel.RATE_PER_KILOMETER)
            .build();

    public static final VehicleModelResponse VEHICLE_MODEL_RESPONSE = VehicleModelResponse.builder()
            .vehicleModelId(FakeVehicleModel.VEHICLE_MODEL_ID)
            .name(FakeVehicleModel.VEHICLE_MODEL_NAME)
            .seats(FakeVehicleModel.VEHICLE_MODEL_SEATS)
            .transmission(FakeVehicleModel.VEHICLE_MODEL_TRANSMISSION_TYPE.toString())
            .engine(FakeVehicleModel.VEHICLE_MODEL_ENGINE_TYPE.toString())
            .rates(RATE_TABLE_RESPONSE)
            .build();
}
