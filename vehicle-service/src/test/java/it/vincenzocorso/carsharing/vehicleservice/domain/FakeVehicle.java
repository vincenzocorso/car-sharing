package it.vincenzocorso.carsharing.vehicleservice.domain;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FakeVehicle {
    public static final String VEHICLE_ID = "640623e3b83509d95802417b";
    public static final String LICENSE_PLATE = "AB123FG";
    public static final String VEHICLE_MODEL_ID = "64062406b83509d95802417e";
    public static final String VEHICLE_MODEL_NAME = "Fiat 500";
    public static final Integer VEHICLE_MODEL_SEATS = 4;
    public static final TransmissionType VEHICLE_MODEL_TRANSMISSION_TYPE = TransmissionType.MANUAL;
    public static final EngineType VEHICLE_MODEL_ENGINE_TYPE = EngineType.ELECTRIC;
    public static final double RATE_PER_MINUTE = 0.19;
    public static final double RATE_PER_HOUR = 9.99;
    public static final double RATE_PER_DAY = 49.99;
    public static final double RATE_PER_KILOMETER = 0.19;
    public static final double LATITUDE = 45.4642;
    public static final double LONGITUDE = 9.1899;
    public static final double AUTONOMY = 84.5;
    public static final Instant UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    public static final String UNLOCK_CODE = "F6J8";
    public static final VehicleModelDetails VEHICLE_MODEL_DETAILS = makeVehicleModelDetails();
    public static final RateTable RATE_TABLE = makeRateTable();
    public static final VehicleModel VEHICLE_MODEL = new VehicleModel(VEHICLE_MODEL_ID, VEHICLE_MODEL_DETAILS, RATE_TABLE);
    public static final VehicleStatus VEHICLE_STATUS = makeVehicleStatus();
    public static final VehicleState VEHICLE_STATE = VehicleState.AVAILABLE;
    public static final Vehicle VEHICLE = new Vehicle(VEHICLE_ID, LICENSE_PLATE, VEHICLE_MODEL_ID, VEHICLE_STATUS, VEHICLE_STATE, UNLOCK_CODE);

    public static Vehicle vehicleWithUnlockCode(String unlockCode) {
        return vehicleWithUnlockCode(VEHICLE_STATE, unlockCode);
    }

    public static Vehicle vehicleWithUnlockCode(VehicleState state, String unlockCode) {
        return new Vehicle(VEHICLE_ID, LICENSE_PLATE, VEHICLE_MODEL_ID, VEHICLE_STATUS, state, unlockCode);
    }

    public static Vehicle vehicleInState(VehicleState state) {
        return new Vehicle(VEHICLE_ID, LICENSE_PLATE, VEHICLE_MODEL_ID, VEHICLE_STATUS, state, UNLOCK_CODE);
    }

    public static Vehicle vehicleInPosition(double latitude, double longitude) {
        var status = VehicleStatus.builder()
                .latitude(latitude)
                .longitude(longitude)
                .autonomy(AUTONOMY)
                .updatedAt(UPDATED_AT)
                .build();
        return new Vehicle(VEHICLE_ID, LICENSE_PLATE, VEHICLE_MODEL_ID, status, VEHICLE_STATE, UNLOCK_CODE);
    }

    public static VehicleModel vehicleModelWithRandomName() {
        VehicleModelDetails details = VehicleModelDetails.builder()
                .name(VEHICLE_MODEL_NAME + UUID.randomUUID().toString())
                .seats(VEHICLE_MODEL_SEATS)
                .transmissionType(VEHICLE_MODEL_TRANSMISSION_TYPE)
                .engineType(VEHICLE_MODEL_ENGINE_TYPE)
                .build();
        return new VehicleModel(VEHICLE_MODEL_ID, details, RATE_TABLE);
    }

    public static void assertEqualsWithVehicle(Vehicle actualVehicle) {
        assertEquals(VEHICLE_ID, actualVehicle.getId());
        assertEquals(LICENSE_PLATE, actualVehicle.getLicensePlate());
        assertEquals(VEHICLE_MODEL_ID, actualVehicle.getVehicleModelId());
        assertEquals(UNLOCK_CODE, actualVehicle.getUnlockCode());
        assertEquals(LATITUDE, actualVehicle.getStatus().latitude());
        assertEquals(LONGITUDE, actualVehicle.getStatus().longitude());
        assertEquals(AUTONOMY, actualVehicle.getStatus().autonomy());
        assertEquals(UPDATED_AT, actualVehicle.getStatus().updatedAt());
        assertEquals(VEHICLE_STATE, actualVehicle.getCurrentState());
    }

    // TODO: unused?
    private static void assertEqualsWithVehicleModel(VehicleModel actualVehicleModel) {
        assertEquals(VEHICLE_MODEL_ID, actualVehicleModel.getId());

        assertEquals(VEHICLE_MODEL_NAME, actualVehicleModel.getDetails().name());
        assertEquals(VEHICLE_MODEL_SEATS, actualVehicleModel.getDetails().seats());
        assertEquals(VEHICLE_MODEL_TRANSMISSION_TYPE, actualVehicleModel.getDetails().transmissionType());
        assertEquals(VEHICLE_MODEL_ENGINE_TYPE, actualVehicleModel.getDetails().engineType());

        assertEquals(RATE_PER_MINUTE, actualVehicleModel.getRateTable().ratePerMinute());
        assertEquals(RATE_PER_HOUR, actualVehicleModel.getRateTable().ratePerHour());
        assertEquals(RATE_PER_DAY, actualVehicleModel.getRateTable().ratePerDay());
        assertEquals(RATE_PER_KILOMETER, actualVehicleModel.getRateTable().ratePerKilometer());
    }

    private static VehicleModelDetails makeVehicleModelDetails() {
        return VehicleModelDetails.builder()
                .name(VEHICLE_MODEL_NAME)
                .seats(VEHICLE_MODEL_SEATS)
                .transmissionType(VEHICLE_MODEL_TRANSMISSION_TYPE)
                .engineType(VEHICLE_MODEL_ENGINE_TYPE)
                .build();
    }

    private static RateTable makeRateTable() {
        return RateTable.builder()
                .ratePerMinute(RATE_PER_MINUTE)
                .ratePerHour(RATE_PER_HOUR)
                .ratePerDay(RATE_PER_DAY)
                .ratePerKilometer(RATE_PER_KILOMETER)
                .build();
    }

    private static VehicleStatus makeVehicleStatus() {
        return VehicleStatus.builder()
                .latitude(LATITUDE)
                .longitude(LONGITUDE)
                .autonomy(AUTONOMY)
                .updatedAt(UPDATED_AT)
                .build();
    }
}
