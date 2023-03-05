package it.vincenzocorso.carsharing.vehicleservice.domain;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;
import it.vincenzocorso.carsharing.common.messaging.events.DomainEventProducer;
import it.vincenzocorso.carsharing.vehicleservice.domain.exceptions.VehicleNotFoundException;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.SearchVehicleCriteria;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.Vehicle;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.VehicleModel;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.VehicleStatus;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.in.RentVehicleUseCase;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.in.SearchVehicleUseCase;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.in.UpdateVehicleStatusUseCase;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.out.RemoteVehicleControlProxy;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.out.VehicleModelRepository;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.out.VehicleRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class VehicleService implements RentVehicleUseCase, SearchVehicleUseCase, UpdateVehicleStatusUseCase {
    public static final String EVENTS_CHANNEL = "vehicle-service-events";
    final VehicleRepository vehicleRepository;
    final VehicleModelRepository vehicleModelRepository;
    final RemoteVehicleControlProxy remoteVehicleControlProxy;
    final DomainEventProducer domainEventProducer;

    @Override
    public Vehicle bookVehicle(String vehicleId) {
        Vehicle vehicle = this.vehicleRepository.findById(vehicleId).orElseThrow(() -> new VehicleNotFoundException(vehicleId));

        List<DomainEvent> events = vehicle.book();
        Vehicle savedVehicle = this.vehicleRepository.save(vehicle);

        this.domainEventProducer.publish(EVENTS_CHANNEL, savedVehicle.getId(), events);

        this.remoteVehicleControlProxy.notifyUnlockCode(vehicleId, vehicle.getUnlockCode());

        return savedVehicle;
    }

    @Override
    public List<Vehicle> getVehicles(SearchVehicleCriteria criteria) {
        return this.vehicleRepository.findByCriteria(criteria);
    }

    @Override
    public Vehicle getVehicle(String vehicleId) {
        return this.vehicleRepository.findById(vehicleId).orElseThrow(() -> new VehicleNotFoundException(vehicleId));
    }

    @Override
    public List<VehicleModel> getVehicleModels() {
        return this.vehicleModelRepository.findAll();
    }

    @Override
    public void updateVehicleStatus(String vehicleId, VehicleStatus newStatus) {
        Vehicle vehicle = this.vehicleRepository.findById(vehicleId).orElseThrow(() -> new VehicleNotFoundException(vehicleId));

        vehicle.updateStatus(newStatus);
        this.vehicleRepository.save(vehicle);
    }
}
