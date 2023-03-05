package it.vincenzocorso.carsharing.vehicleservice.domain.models;

import it.vincenzocorso.carsharing.vehicleservice.domain.exceptions.IllegalVehicleStateTransitionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicle.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {
    @Test
    void shouldBookVehicle() {
        Vehicle vehicle = vehicleInState(VehicleState.AVAILABLE);

        vehicle.book();

        assertThat(vehicle.getCurrentState()).isEqualTo(VehicleState.BOOKED);
    }

    @Test
    void shouldGenerateValidUnlockCode() {
        Vehicle vehicle = vehicleWithUnlockCode(VehicleState.AVAILABLE, null);

        vehicle.book();

        String unlockCode = vehicle.getUnlockCode();
        assertThat(unlockCode)
                .isNotNull()
                .hasSize(4)
                .containsPattern("^[a-zA-Z0-9]*$");
    }

    @ParameterizedTest
    @EnumSource(value = VehicleState.class, mode = EnumSource.Mode.EXCLUDE, names = {"AVAILABLE"})
    void shouldNotBookVehicle(VehicleState state) {
        Vehicle vehicle = vehicleInState(state);

        assertThrows(IllegalVehicleStateTransitionException.class, vehicle::book);
    }
}
