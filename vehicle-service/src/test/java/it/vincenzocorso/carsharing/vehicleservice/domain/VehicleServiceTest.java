package it.vincenzocorso.carsharing.vehicleservice.domain;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;
import it.vincenzocorso.carsharing.common.messaging.events.DomainEventProducer;
import it.vincenzocorso.carsharing.vehicleservice.domain.events.VehicleStateTransitionEvent;
import it.vincenzocorso.carsharing.vehicleservice.domain.exceptions.IllegalVehicleStateTransitionException;
import it.vincenzocorso.carsharing.vehicleservice.domain.exceptions.VehicleNotFoundException;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.*;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.out.RemoteVehicleControlProxy;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.out.VehicleModelRepository;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.out.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicle.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {
    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleModelRepository vehicleModelRepository;

    @Mock
    private RemoteVehicleControlProxy remoteVehicleControlProxy;

    @Mock
    private DomainEventProducer domainEventProducer;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void shouldBookVehicle() {
        DomainEvent event = new VehicleStateTransitionEvent(VehicleState.AVAILABLE.toString(), VehicleState.BOOKED.toString());
        Vehicle persistedVehicle = vehicleInState(VehicleState.AVAILABLE);
        when(this.vehicleRepository.findById(VEHICLE_ID)).thenReturn(Optional.of(persistedVehicle));
        when(this.vehicleRepository.save(any(Vehicle.class))).thenReturn(persistedVehicle);

        this.vehicleService.bookVehicle(VEHICLE_ID);

        verify(this.vehicleRepository).save(persistedVehicle);
        verify(this.domainEventProducer).publish(VehicleService.EVENTS_CHANNEL, VEHICLE_ID, List.of(event));
        verify(this.remoteVehicleControlProxy).notifyUnlockCode(VEHICLE_ID, persistedVehicle.getUnlockCode());
    }

    @Test
    void shouldNotBookVehicleWhenVehicleIsNotAvailable() {
        Vehicle persistedVehicle = vehicleInState(VehicleState.BOOKED);
        when(this.vehicleRepository.findById(VEHICLE_ID)).thenReturn(Optional.of(persistedVehicle));

        assertThrows(IllegalVehicleStateTransitionException.class, () -> this.vehicleService.bookVehicle(VEHICLE_ID));
    }

    @Test
    void shouldNotBookVehicleWhenVehicleDoesNotExist() {
        when(this.vehicleRepository.findById(VEHICLE_ID)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> this.vehicleService.bookVehicle(VEHICLE_ID));
    }

    @Test
    void shouldGetVehicles() {
        List<Vehicle> persistedVehicles = List.of(VEHICLE);
        SearchVehicleCriteria criteria = SearchVehicleCriteria.empty();
        when(this.vehicleRepository.findByCriteria(criteria)).thenReturn(persistedVehicles);

        List<Vehicle> retrievedVehicles = this.vehicleService.getVehicles(criteria);

        assertThat(retrievedVehicles).hasSameElementsAs(persistedVehicles);
    }

    @Test
    void shouldGetVehicle() {
        when(this.vehicleRepository.findById(VEHICLE_ID)).thenReturn(Optional.of(VEHICLE));

        Vehicle retrievedVehicle = this.vehicleService.getVehicle(VEHICLE_ID);

        assertThat(retrievedVehicle).usingRecursiveComparison().isEqualTo(VEHICLE);
    }

    @Test
    void shouldNotGetVehicleWhenDoesNotExist() {
        when(this.vehicleRepository.findById(VEHICLE_ID)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> this.vehicleService.getVehicle(VEHICLE_ID));
    }

    @Test
    void shouldGetVehicleModels() {
        List<VehicleModel> persistedVehicleModels = List.of(VEHICLE_MODEL);
        when(this.vehicleModelRepository.findAll()).thenReturn(persistedVehicleModels);

        List<VehicleModel> retrievedVehicleModels = this.vehicleService.getVehicleModels();

        assertThat(retrievedVehicleModels).hasSameElementsAs(persistedVehicleModels);
    }

    @Test
    void shouldUpdateVehicleStatus() {
        Vehicle vehicle = vehicleInState(VehicleState.BOOKED);
        VehicleStatus updatedStatus = VehicleStatus.builder()
                .latitude(LATITUDE + 1)
                .longitude(LONGITUDE + 1)
                .autonomy(AUTONOMY - 5)
                .updatedAt(UPDATED_AT.plus(1, ChronoUnit.HOURS))
                .build();
        when(this.vehicleRepository.findById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(this.vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        this.vehicleService.updateVehicleStatus(VEHICLE_ID, updatedStatus);

        verify(this.vehicleRepository).save(vehicle);

        assertThat(vehicle.getStatus()).usingRecursiveComparison().isEqualTo(updatedStatus);
    }

    @Test
    void shouldNotUpdateVehicleStatusWhenVehicleDoesNotExist() {
        when(this.vehicleRepository.findById(VEHICLE_ID)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> this.vehicleService.updateVehicleStatus(VEHICLE_ID, VEHICLE_STATUS));
    }
}
