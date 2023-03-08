package it.vincenzocorso.carsharing.vehicleservice.domain;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class FakeVehicle {
    public static final String VEHICLE_ID = "640623e3b83509d95802417b";
    public static final String LICENSE_PLATE = "AB123FG";
    public static final double LATITUDE = 45.4642;
    public static final double LONGITUDE = 9.1899;
    public static final double AUTONOMY = 84.5;
    public static final Instant UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    public static final String UNLOCK_CODE = "F6J8";
    public static final VehicleModelDetails VEHICLE_MODEL_DETAILS = FakeVehicleModel.makeVehicleModelDetails();
    public static final RateTable RATE_TABLE = FakeVehicleModel.makeRateTable();
    public static final VehicleModel VEHICLE_MODEL = new VehicleModel(FakeVehicleModel.VEHICLE_MODEL_ID, VEHICLE_MODEL_DETAILS, RATE_TABLE);
    public static final VehicleStatus VEHICLE_STATUS = makeVehicleStatus();
    public static final VehicleState VEHICLE_STATE = VehicleState.AVAILABLE;
    public static final Vehicle VEHICLE = new Vehicle(VEHICLE_ID, LICENSE_PLATE, FakeVehicleModel.VEHICLE_MODEL_ID, VEHICLE_STATUS, VEHICLE_STATE, UNLOCK_CODE);

    public static Vehicle vehicleWithUnlockCode(VehicleState state, String unlockCode) {
        return new Vehicle(VEHICLE_ID, LICENSE_PLATE, FakeVehicleModel.VEHICLE_MODEL_ID, VEHICLE_STATUS, state, unlockCode);
    }

    public static Vehicle vehicleInState(VehicleState state) {
        return new Vehicle(VEHICLE_ID, LICENSE_PLATE, FakeVehicleModel.VEHICLE_MODEL_ID, VEHICLE_STATUS, state, UNLOCK_CODE);
    }

    public static Vehicle vehicleInPosition(double latitude, double longitude) {
        var status = VehicleStatus.builder()
                .latitude(latitude)
                .longitude(longitude)
                .autonomy(AUTONOMY)
                .updatedAt(UPDATED_AT)
                .build();
        return new Vehicle(VEHICLE_ID, LICENSE_PLATE, FakeVehicleModel.VEHICLE_MODEL_ID, status, VEHICLE_STATE, UNLOCK_CODE);
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
