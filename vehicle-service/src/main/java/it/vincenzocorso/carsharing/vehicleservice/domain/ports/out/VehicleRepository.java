package it.vincenzocorso.carsharing.vehicleservice.domain.ports.out;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.SearchVehicleCriteria;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {
    /**
     * Gets the vehicle with the given id
     * @param vehicleId The vehicle id
     * @return The vehicle with the given id
     */
    Optional<Vehicle> findById(String vehicleId);

    /**
     * Gets all the vehicles with the given criteria
     * @param criteria The search criteria. Null fields are not considered
     * @return The list of all vehicles which respect these criteria
     * @see SearchVehicleCriteria
     */
    List<Vehicle> findByCriteria(SearchVehicleCriteria criteria);

    /**
     * Save the given vehicle
     * @param vehicle The vehicle to save
     * @return The saved vehicle
     */
    Vehicle save(Vehicle vehicle);
}
