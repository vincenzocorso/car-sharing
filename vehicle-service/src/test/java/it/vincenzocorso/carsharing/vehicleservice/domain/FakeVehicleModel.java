package it.vincenzocorso.carsharing.vehicleservice.domain;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FakeVehicleModel {
    public static final String VEHICLE_MODEL_ID = "64062406b83509d95802417e";
    public static final String VEHICLE_MODEL_NAME = "Fiat 500";
    public static final Integer VEHICLE_MODEL_SEATS = 4;
    public static final TransmissionType VEHICLE_MODEL_TRANSMISSION_TYPE = TransmissionType.MANUAL;
    public static final EngineType VEHICLE_MODEL_ENGINE_TYPE = EngineType.ELECTRIC;
    public static final double RATE_PER_MINUTE = 0.19;
    public static final double RATE_PER_HOUR = 9.99;
    public static final double RATE_PER_DAY = 49.99;
    public static final double RATE_PER_KILOMETER = 0.19;

    public static VehicleModel vehicleModelWithRandomName() {
        VehicleModelDetails details = VehicleModelDetails.builder()
                .name(VEHICLE_MODEL_NAME + UUID.randomUUID().toString())
                .seats(VEHICLE_MODEL_SEATS)
                .transmissionType(VEHICLE_MODEL_TRANSMISSION_TYPE)
                .engineType(VEHICLE_MODEL_ENGINE_TYPE)
                .build();
        return new VehicleModel(VEHICLE_MODEL_ID, details, FakeVehicle.RATE_TABLE);
    }

    static VehicleModelDetails makeVehicleModelDetails() {
        return VehicleModelDetails.builder()
                .name(VEHICLE_MODEL_NAME)
                .seats(VEHICLE_MODEL_SEATS)
                .transmissionType(VEHICLE_MODEL_TRANSMISSION_TYPE)
                .engineType(VEHICLE_MODEL_ENGINE_TYPE)
                .build();
    }

    static RateTable makeRateTable() {
        return RateTable.builder()
                .ratePerMinute(RATE_PER_MINUTE)
                .ratePerHour(RATE_PER_HOUR)
                .ratePerDay(RATE_PER_DAY)
                .ratePerKilometer(RATE_PER_KILOMETER)
                .build();
    }
}
