package it.vincenzocorso.carsharing.vehicleservice.adapters.persistence.mongodb;

import com.mongodb.client.model.Indexes;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.quarkus.test.junit.QuarkusTest;
import it.vincenzocorso.carsharing.vehicleservice.domain.exceptions.VehicleNotFoundException;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.SearchVehicleCriteria;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.Vehicle;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.VehicleState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicle.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(VehicleRepositoryAdapterIntegrationTest.ContainerInitializer.class)
class VehicleRepositoryAdapterIntegrationTest {
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
    VehicleRepositoryAdapter vehicleRepositoryAdapter;

    @Inject
    VehicleDocumentMapper vehicleDocumentMapper;

    @BeforeAll
    static void createIndexes() {
        VehicleDocument.mongoCollection().createIndex(Indexes.geo2dsphere("position"));
    }

    @AfterEach
    void cleanup() {
        VehicleDocument.deleteAll();
    }

    @Test
    void shouldFindVehicleById() {
        this.vehicleRepositoryAdapter.save(VEHICLE);

        Optional<Vehicle> optionalVehicle = this.vehicleRepositoryAdapter.findById(VEHICLE_ID);

        assertTrue(optionalVehicle.isPresent());
        Vehicle retrievedVehicle = optionalVehicle.get();
        assertThat(retrievedVehicle).usingRecursiveComparison().isEqualTo(VEHICLE);
    }

    @Test
    void shouldThrowVehicleNotFoundExceptionWhenIdIsNotAValidObjectId() {
        assertThrows(VehicleNotFoundException.class, () -> this.vehicleRepositoryAdapter.findById("INVALID_ID"));
    }

    @Test
    void shouldFindAllVehicles() {
        List<Vehicle> savedVehicles = this.initializeVehiclesCollection();
        List<String> expectedVehicleIds = savedVehicles.stream().map(Vehicle::getId).toList();
        SearchVehicleCriteria searchCriteria = SearchVehicleCriteria.empty();

        List<String> retrievedVehicleIds = this.vehicleRepositoryAdapter.findByCriteria(searchCriteria).stream().map(Vehicle::getId).toList();

        assertThat(retrievedVehicleIds).hasSameElementsAs(expectedVehicleIds);
    }

    @Test
    void shouldFindVehiclesByPagination() {
        List<Vehicle> savedVehicles = this.initializeVehiclesCollection();
        SearchVehicleCriteria searchCriteria = SearchVehicleCriteria.builder().offset(1).limit(2).build();

        List<String> retrievedVehicleIds = this.vehicleRepositoryAdapter.findByCriteria(searchCriteria).stream().map(Vehicle::getId).toList();

        assertThat(retrievedVehicleIds).hasSize(savedVehicles.size() - 1);
    }

    @Test
    void shouldFindVehiclesByPosition() {
        this.initializeVehiclesCollection();
        Vehicle nearVehicle = vehicleInPosition(0.0, 1.0);
        this.vehicleRepositoryAdapter.save(nearVehicle);
        SearchVehicleCriteria searchCriteria = SearchVehicleCriteria.builder()
                .latitude(0.0)
                .longitude(1.0)
                .radius(100.0)
                .build();

        List<String> retrievedVehicleIds = this.vehicleRepositoryAdapter.findByCriteria(searchCriteria).stream().map(Vehicle::getId).toList();

        assertThat(retrievedVehicleIds).singleElement().isEqualTo(nearVehicle.getId());
    }

    @Test
    void shouldFindVehiclesByState() {
        Vehicle expectedVehicle = vehicleInState(VehicleState.AVAILABLE);
        Vehicle notExpectedVehicle = vehicleInState(VehicleState.RENTED);
        this.vehicleRepositoryAdapter.save(expectedVehicle);
        this.vehicleRepositoryAdapter.save(notExpectedVehicle);
        SearchVehicleCriteria searchCriteria = SearchVehicleCriteria.builder()
                .states(List.of(VehicleState.AVAILABLE))
                .build();

        List<String> retrievedVehicleIds = this.vehicleRepositoryAdapter.findByCriteria(searchCriteria).stream().map(Vehicle::getId).toList();

        assertThat(retrievedVehicleIds).singleElement().isEqualTo(expectedVehicle.getId());
    }

    @Test
    void shouldSave() {
        Vehicle savedVehicle = this.vehicleRepositoryAdapter.save(VEHICLE);

        assertThat(VehicleDocument.count()).isEqualTo(1);
        assertThat(savedVehicle).usingRecursiveComparison().isEqualTo(VEHICLE);
    }

    private List<Vehicle> initializeVehiclesCollection() {
        return Stream.of(vehicleInState(VehicleState.AVAILABLE), vehicleInState(VehicleState.RENTED), vehicleInState(VehicleState.BOOKED))
                .peek(vehicle -> vehicle.setId(null))
                .map(this.vehicleDocumentMapper::convertToDocument)
                .peek(vehicleDocument -> vehicleDocument.persist())
                .map(this.vehicleDocumentMapper::convertFromDocument)
                .toList();
    }
}
