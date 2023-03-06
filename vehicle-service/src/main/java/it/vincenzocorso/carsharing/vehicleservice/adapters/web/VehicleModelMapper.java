package it.vincenzocorso.carsharing.vehicleservice.adapters.web;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.RateTable;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.VehicleModel;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.VehicleModelDetails;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VehicleModelMapper {
    public VehicleModelResponse convertToDto(VehicleModel vehicleModel) {
        RateTable rateTable = vehicleModel.getRateTable();
        RateTableResponse rateTableResponse = RateTableResponse.builder()
                .ratePerMinute(rateTable.ratePerMinute())
                .ratePerHour(rateTable.ratePerHour())
                .ratePerDay(rateTable.ratePerDay())
                .ratePerKilometer(rateTable.ratePerKilometer())
                .build();

        VehicleModelDetails modelDetails = vehicleModel.getDetails();
        return VehicleModelResponse.builder()
                .vehicleModelId(vehicleModel.getId())
                .name(modelDetails.name())
                .seats(modelDetails.seats())
                .transmission(modelDetails.transmissionType().toString())
                .engine(modelDetails.engineType().toString())
                .rates(rateTableResponse)
                .build();
    }
}
