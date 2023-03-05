package it.vincenzocorso.carsharing.vehicleservice.domain.models;

import lombok.Builder;

import java.util.List;

/**
 * This class represents the search criteria for vehicles
 */
@Builder
public class SearchVehicleCriteria {
    /**
     * The latitude of the search area
     */
    public final Double latitude;

    /**
     * The longitude of the search area
     */
    public final Double longitude;

    /**
     * The radius of the search area
     */
    public final Double radius;

    /**
     * The states of the vehicles to search
     */
    public final List<VehicleState> states;

    /**
     * The maximum number of results to return
     */
    public final Integer limit;

    /**
     * The number of results to omit from the beginning
     */
    public final Integer offset;

    public static SearchVehicleCriteria empty() {
        return new SearchVehicleCriteriaBuilder().build();
    }
}
