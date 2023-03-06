package it.vincenzocorso.carsharing.vehicleservice.adapters.persistence.jpa;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.VehicleModel;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.out.VehicleModelRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class VehicleModelJPARepositoryAdapter implements VehicleModelRepository {
    @Override
    public List<VehicleModel> findAll() {
        return List.of();
    }
}
