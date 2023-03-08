package it.vincenzocorso.carsharing.vehicleservice.adapters.persistence.mongodb;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.quarkus.test.junit.QuarkusTest;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.VehicleModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicle.vehicleModelWithRandomName;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@QuarkusTestResource(VehicleModelRepositoryAdapterIntegrationTest.ContainerInitializer.class)
class VehicleModelRepositoryAdapterIntegrationTest {
    @Container
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.8");

    public static class ContainerInitializer implements QuarkusTestResourceLifecycleManager {
        @Override
        public Map<String, String> start() {
            mongoDBContainer.start();
            return Map.of(
            "quarkus.mongodb.connection-string", "mongodb://" + mongoDBContainer.getContainerIpAddress() + ":" + mongoDBContainer.getFirstMappedPort() + "/vehicles",
            "quarkus.mongodb.database", "vehicles"
            );
        }

        @Override
        public void stop() {
            mongoDBContainer.stop();
        }
    }

    @Inject
    VehicleModelRepositoryAdapter vehicleModelRepositoryAdapter;

    @Inject
    VehicleModelDocumentMapper vehicleModelDocumentMapper;

    @AfterEach
    void cleanup() {
        VehicleModelDocument.deleteAll();
    }

    @Test
    void shouldFindAllVehicleModels() {
        List<VehicleModel> savedVehicleModels = this.initializeVehicleModelsCollection();
        List<String> expectedVehicleModelIds = savedVehicleModels.stream().map(VehicleModel::getId).toList();

        List<String> retrievedVehicleModelIds = this.vehicleModelRepositoryAdapter.findAll().stream().map(VehicleModel::getId).toList();

        assertThat(retrievedVehicleModelIds)
                .hasSameElementsAs(expectedVehicleModelIds);
    }

    private List<VehicleModel> initializeVehicleModelsCollection() {
        return Stream.of(vehicleModelWithRandomName(), vehicleModelWithRandomName(), vehicleModelWithRandomName())
                .peek(vehicleModel -> vehicleModel.setId(null))
                .map(this.vehicleModelDocumentMapper::convertToDocument)
                .peek(document -> document.persist())
                .map(this.vehicleModelDocumentMapper::convertFromDocument)
                .toList();
    }
}
