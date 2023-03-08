package it.vincenzocorso.carsharing.vehicleservice.domain.models;

import lombok.Getter;

@Getter
public class VehicleModel {
    private String id;
    private VehicleModelDetails details;
    private RateTable rateTable;

    public VehicleModel(String id, VehicleModelDetails details, RateTable rateTable) {
        this.id = id;
        this.details = details;
        this.rateTable = rateTable;
    }

    public void setId(String id) {
        this.id = id;
    }
}
