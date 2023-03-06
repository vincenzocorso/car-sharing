package it.vincenzocorso.carsharing.vehicleservice.adapters.persistence.jpa;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.SearchVehicleCriteria;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.Vehicle;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.out.VehicleRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class VehicleJPARepositoryAdapter implements VehicleRepository {
    @Override
    public Optional<Vehicle> findById(String vehicleId) {
        return Optional.empty();
    }

    @Override
    public List<Vehicle> findByCriteria(SearchVehicleCriteria criteria) {
        return List.of();
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicle;
    }
}
