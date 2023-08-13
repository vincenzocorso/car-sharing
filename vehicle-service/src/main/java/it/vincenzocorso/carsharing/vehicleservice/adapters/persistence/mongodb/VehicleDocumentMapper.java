package it.vincenzocorso.carsharing.vehicleservice.adapters.persistence.mongodb;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.Vehicle;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.VehicleState;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.VehicleStatus;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class VehicleDocumentMapper {
    public VehicleDocument convertToDocument(Vehicle vehicle) {
        VehicleDocument vehicleDocument = new VehicleDocument();
        vehicleDocument.id = vehicle.getId() != null ? new ObjectId(vehicle.getId()) : null;
        vehicleDocument.licensePlate = vehicle.getLicensePlate();
        vehicleDocument.vehicleModelId = new ObjectId(vehicle.getVehicleModelId());
        vehicleDocument.position = new Point(new Position(vehicle.getStatus().longitude(), vehicle.getStatus().latitude()));
        vehicleDocument.autonomy = vehicle.getStatus().autonomy();
        vehicleDocument.lastStatusUpdate = vehicle.getStatus().updatedAt().toEpochMilli();
        vehicleDocument.currentState = vehicle.getCurrentState().toString();
        vehicleDocument.unlockCode = vehicle.getUnlockCode();
        return vehicleDocument;
    }

    public Vehicle convertFromDocument(VehicleDocument vehicleDocument) {
        List<Double> position = vehicleDocument.position.getPosition().getValues();

        VehicleStatus vehicleStatus = VehicleStatus.builder()
            .latitude(position.get(1))
            .longitude(position.get(0))
            .autonomy(vehicleDocument.autonomy)
            .updatedAt(Instant.ofEpochMilli(vehicleDocument.lastStatusUpdate))
            .build();

        String vehicleId = vehicleDocument.id != null ? vehicleDocument.id.toString() : null;

        return new Vehicle(
            vehicleId,
            vehicleDocument.licensePlate,
            vehicleDocument.vehicleModelId.toHexString(),
            vehicleStatus,
            VehicleState.valueOf(vehicleDocument.currentState),
            vehicleDocument.unlockCode
        );
    }
}
