package it.vincenzocorso.carsharing.vehicleservice.adapters.web;

import it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicleModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicle.*;

class VehicleModelMapperTest {
    private final VehicleModelMapper vehicleModelMapper = new VehicleModelMapper();

    @Test
    void shouldConvertToDto() {
        RateTableResponse expectedRateTableResponse = RateTableResponse.builder()
                .ratePerMinute(FakeVehicleModel.RATE_PER_MINUTE)
                .ratePerHour(FakeVehicleModel.RATE_PER_HOUR)
                .ratePerDay(FakeVehicleModel.RATE_PER_DAY)
                .ratePerKilometer(FakeVehicleModel.RATE_PER_KILOMETER)
                .build();
        VehicleModelResponse expectedVehicleModelResponse = VehicleModelResponse.builder()
                .vehicleModelId(FakeVehicleModel.VEHICLE_MODEL_ID)
                .name(FakeVehicleModel.VEHICLE_MODEL_NAME)
                .seats(FakeVehicleModel.VEHICLE_MODEL_SEATS)
                .transmission(FakeVehicleModel.VEHICLE_MODEL_TRANSMISSION_TYPE.toString())
                .engine(FakeVehicleModel.VEHICLE_MODEL_ENGINE_TYPE.toString())
                .rates(expectedRateTableResponse)
                .build();

        VehicleModelResponse actualVehicleModelResponse = this.vehicleModelMapper.convertToDto(VEHICLE_MODEL);

        Assertions.assertThat(actualVehicleModelResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedVehicleModelResponse);
    }
}
