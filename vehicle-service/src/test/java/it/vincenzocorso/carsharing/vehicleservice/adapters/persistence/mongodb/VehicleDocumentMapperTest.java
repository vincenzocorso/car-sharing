package it.vincenzocorso.carsharing.vehicleservice.adapters.persistence.mongodb;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.Vehicle;
import org.junit.jupiter.api.Test;

import static it.vincenzocorso.carsharing.vehicleservice.adapters.persistence.mongodb.FakeVehicleEntity.VEHICLE_DOCUMENT;
import static it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicle.VEHICLE;
import static org.assertj.core.api.Assertions.assertThat;

class VehicleDocumentMapperTest {
    private final VehicleDocumentMapper vehicleDocumentMapper = new VehicleDocumentMapper();

    @Test
    void shouldConvertToDocument() {
        VehicleDocument actualVehicleDocument = this.vehicleDocumentMapper.convertToDocument(VEHICLE);

        assertThat(actualVehicleDocument)
                .usingRecursiveComparison()
                .isEqualTo(VEHICLE_DOCUMENT);
    }

    @Test
    void shouldConvertFromDocument() {
        Vehicle actualVehicle = this.vehicleDocumentMapper.convertFromDocument(VEHICLE_DOCUMENT);

        assertThat(actualVehicle)
                .usingRecursiveComparison()
                .isEqualTo(VEHICLE);
    }
}
