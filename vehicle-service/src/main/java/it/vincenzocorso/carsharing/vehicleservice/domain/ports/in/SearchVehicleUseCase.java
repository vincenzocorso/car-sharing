package it.vincenzocorso.carsharing.vehicleservice.domain.ports.in;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.SearchVehicleCriteria;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.Vehicle;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.VehicleModel;

import java.util.List;

public interface SearchVehicleUseCase {
    /**
     * Gets all the vehicles with the given criteria
     * @params criteria The search criteria. Null attributes are not considered
     * @return The list of all vehicles which respect these criteria
     * @see SearchVehicleCriteria
     */
    List<Vehicle> getVehicles(SearchVehicleCriteria criteria);

    /**
     * Gets the vehicle with the given id
     * @param vehicleId The vehicle id
     * @return The vehicle with the given id
     * @throws it.vincenzocorso.carsharing.vehicleservice.domain.exceptions.VehicleNotFoundException If the vehicle with the given id does not exist
     */
    Vehicle getVehicle(String vehicleId);

    /**
     * Gets all the vehicle models
     * @return The list of all vehicle models
     */
    List<VehicleModel> getVehicleModels();
}
