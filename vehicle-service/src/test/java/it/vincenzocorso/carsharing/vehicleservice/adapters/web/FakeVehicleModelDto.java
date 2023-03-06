package it.vincenzocorso.carsharing.vehicleservice.adapters.web;

import static it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicle.*;

public class FakeVehicleModelDto {
    public static final RateTableResponse RATE_TABLE_RESPONSE = RateTableResponse.builder()
            .ratePerMinute(RATE_PER_MINUTE)
            .ratePerHour(RATE_PER_HOUR)
            .ratePerDay(RATE_PER_DAY)
            .ratePerKilometer(RATE_PER_KILOMETER)
            .build();

    public static final VehicleModelResponse VEHICLE_MODEL_RESPONSE = VehicleModelResponse.builder()
            .vehicleModelId(VEHICLE_MODEL_ID)
            .name(VEHICLE_MODEL_NAME)
            .seats(VEHICLE_MODEL_SEATS)
            .transmission(VEHICLE_MODEL_TRANSMISSION_TYPE.toString())
            .engine(VEHICLE_MODEL_ENGINE_TYPE.toString())
            .rates(RATE_TABLE_RESPONSE)
            .build();
}
