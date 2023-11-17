package it.vincenzocorso.carsharing.vehicleservice.adapters.persistence.mongodb;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.*;
import org.bson.types.ObjectId;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VehicleModelDocumentMapper {
    public VehicleModelDocument convertToDocument(VehicleModel vehicleModel) {
        VehicleModelDetails details = vehicleModel.getDetails();
        VehicleModelDocument vehicleModelDocument = new VehicleModelDocument();
        vehicleModelDocument.id = vehicleModel.getId() != null ? new ObjectId(vehicleModel.getId()) : null;
        vehicleModelDocument.name = details.name();
        vehicleModelDocument.seats = details.seats();
        vehicleModelDocument.transmissionType = details.transmissionType().toString();
        vehicleModelDocument.engineType = details.engineType().toString();
        vehicleModelDocument.rates = this.convertToDocument(vehicleModel.getRateTable());
        return vehicleModelDocument;
    }

    private RateTableDocument convertToDocument(RateTable rateTable) {
        RateTableDocument rateTableDocument = new RateTableDocument();
        rateTableDocument.ratePerMinute = rateTable.ratePerMinute();
        rateTableDocument.ratePerHour = rateTable.ratePerHour();
        rateTableDocument.ratePerDay = rateTable.ratePerDay();
        rateTableDocument.ratePerKilometer = rateTable.ratePerKilometer();
        return rateTableDocument;
    }

    public VehicleModel convertFromDocument(VehicleModelDocument vehicleModelDocument) {
        VehicleModelDetails details = new VehicleModelDetails(
            vehicleModelDocument.name,
            vehicleModelDocument.seats,
            TransmissionType.valueOf(vehicleModelDocument.transmissionType),
            EngineType.valueOf(vehicleModelDocument.engineType)
        );
        RateTable rateTable = new RateTable(
            vehicleModelDocument.rates.ratePerMinute,
            vehicleModelDocument.rates.ratePerHour,
            vehicleModelDocument.rates.ratePerDay,
            vehicleModelDocument.rates.ratePerKilometer
        );
        String vehicleModelId = vehicleModelDocument.id != null ? vehicleModelDocument.id.toString() : null;
        return new VehicleModel(vehicleModelId, details, rateTable);
    }
}
