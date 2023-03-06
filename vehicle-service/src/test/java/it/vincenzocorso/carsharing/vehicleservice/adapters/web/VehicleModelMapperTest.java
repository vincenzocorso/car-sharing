package it.vincenzocorso.carsharing.vehicleservice.adapters.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicle.*;

class VehicleModelMapperTest {
    private final VehicleModelMapper vehicleModelMapper = new VehicleModelMapper();

    @Test
    void shouldConvertToDto() {
        RateTableResponse expectedRateTableResponse = RateTableResponse.builder()
                .ratePerMinute(RATE_PER_MINUTE)
                .ratePerHour(RATE_PER_HOUR)
                .ratePerDay(RATE_PER_DAY)
                .ratePerKilometer(RATE_PER_KILOMETER)
                .build();
        VehicleModelResponse expectedVehicleModelResponse = VehicleModelResponse.builder()
                .vehicleModelId(VEHICLE_MODEL_ID)
                .name(VEHICLE_MODEL_NAME)
                .seats(VEHICLE_MODEL_SEATS)
                .transmission(VEHICLE_MODEL_TRANSMISSION_TYPE.toString())
                .engine(VEHICLE_MODEL_ENGINE_TYPE.toString())
                .rates(expectedRateTableResponse)
                .build();

        VehicleModelResponse actualVehicleModelResponse = this.vehicleModelMapper.convertToDto(VEHICLE_MODEL);

        Assertions.assertThat(actualVehicleModelResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedVehicleModelResponse);
    }
}
