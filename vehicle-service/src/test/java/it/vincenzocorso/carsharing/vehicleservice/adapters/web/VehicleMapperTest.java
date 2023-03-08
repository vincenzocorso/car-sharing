package it.vincenzocorso.carsharing.vehicleservice.adapters.web;

import it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicleModel;
import org.junit.jupiter.api.Test;

import static it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicle.*;
import static org.assertj.core.api.Assertions.assertThat;

class VehicleMapperTest {
    private final VehicleMapper vehicleMapper = new VehicleMapper();

    @Test
    void shouldConvertToDto() {
        VehiclePosition expectedVehiclePosition = new VehiclePosition(LATITUDE, LONGITUDE);
        VehicleResponse expectedVehicleResponse = VehicleResponse.builder()
                .vehicleId(VEHICLE_ID)
                .licensePlate(LICENSE_PLATE)
                .position(expectedVehiclePosition)
                .autonomy(AUTONOMY)
                .currentState(VEHICLE_STATE.toString())
                .vehicleModelId(FakeVehicleModel.VEHICLE_MODEL_ID)
                .build();

        VehicleResponse actualVehicleResponse = this.vehicleMapper.convertToDto(VEHICLE);

        assertThat(actualVehicleResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedVehicleResponse);
    }
}
