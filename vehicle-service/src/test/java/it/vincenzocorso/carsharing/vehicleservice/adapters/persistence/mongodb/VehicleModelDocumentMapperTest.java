package it.vincenzocorso.carsharing.vehicleservice.adapters.persistence.mongodb;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.VehicleModel;
import org.junit.jupiter.api.Test;

import static it.vincenzocorso.carsharing.vehicleservice.adapters.persistence.mongodb.FakeVehicleEntity.VEHICLE_MODEL_DOCUMENT;
import static it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicle.VEHICLE_MODEL;
import static org.assertj.core.api.Assertions.assertThat;

class VehicleModelDocumentMapperTest {
    private final VehicleModelDocumentMapper vehicleModelDocumentMapper = new VehicleModelDocumentMapper();

    @Test
    void shouldConvertToDocument() {
        VehicleModelDocument actualVehicleModelDocument = this.vehicleModelDocumentMapper.convertToDocument(VEHICLE_MODEL);

        assertThat(actualVehicleModelDocument)
                .usingRecursiveComparison()
                .isEqualTo(VEHICLE_MODEL_DOCUMENT);
    }

    @Test
    void shouldConvertFromDocument() {
        VehicleModel actualVehicleModel = this.vehicleModelDocumentMapper.convertFromDocument(VEHICLE_MODEL_DOCUMENT);

        assertThat(actualVehicleModel)
                .usingRecursiveComparison()
                .isEqualTo(VEHICLE_MODEL);
    }
}
