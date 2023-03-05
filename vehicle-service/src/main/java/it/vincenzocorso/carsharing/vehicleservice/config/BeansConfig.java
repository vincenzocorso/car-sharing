package it.vincenzocorso.carsharing.vehicleservice.config;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEventProducer;
import it.vincenzocorso.carsharing.vehicleservice.domain.VehicleService;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.out.RemoteVehicleControlProxy;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.out.VehicleModelRepository;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.out.VehicleRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class BeansConfig {
    @Produces @ApplicationScoped
    VehicleService vehicleService(
            VehicleRepository vehicleRepository,
            VehicleModelRepository vehicleModelRepository,
            RemoteVehicleControlProxy remoteVehicleControlProxy,
            DomainEventProducer domainEventProducer) {
        return new VehicleService(vehicleRepository, vehicleModelRepository, remoteVehicleControlProxy, domainEventProducer);
    }
}
