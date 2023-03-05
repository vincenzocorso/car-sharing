package it.vincenzocorso.carsharing.vehicleservice.domain.ports.out;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.VehicleModel;

import java.util.List;

public interface VehicleModelRepository {
    /**
     * Gets all the vehicle models
     * @return The list of all vehicle models
     */
    List<VehicleModel> findAll();
}
