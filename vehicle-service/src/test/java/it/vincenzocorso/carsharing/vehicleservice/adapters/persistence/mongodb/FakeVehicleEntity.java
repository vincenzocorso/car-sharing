package it.vincenzocorso.carsharing.vehicleservice.adapters.persistence.mongodb;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicleModel;
import org.bson.types.ObjectId;

import static it.vincenzocorso.carsharing.vehicleservice.domain.FakeVehicle.*;

public class FakeVehicleEntity {
    public static final RateTableDocument RATE_TABLE_DOCUMENT = new RateTableDocument();
    static {
        RATE_TABLE_DOCUMENT.ratePerMinute = FakeVehicleModel.RATE_PER_MINUTE;
        RATE_TABLE_DOCUMENT.ratePerHour = FakeVehicleModel.RATE_PER_HOUR;
        RATE_TABLE_DOCUMENT.ratePerDay = FakeVehicleModel.RATE_PER_DAY;
        RATE_TABLE_DOCUMENT.ratePerKilometer = FakeVehicleModel.RATE_PER_KILOMETER;
    }

    public static final VehicleModelDocument VEHICLE_MODEL_DOCUMENT = new VehicleModelDocument();
    static {
        VEHICLE_MODEL_DOCUMENT.id = new ObjectId(FakeVehicleModel.VEHICLE_MODEL_ID);
        VEHICLE_MODEL_DOCUMENT.name = FakeVehicleModel.VEHICLE_MODEL_NAME;
        VEHICLE_MODEL_DOCUMENT.seats = FakeVehicleModel.VEHICLE_MODEL_SEATS;
        VEHICLE_MODEL_DOCUMENT.transmissionType = FakeVehicleModel.VEHICLE_MODEL_TRANSMISSION_TYPE.toString();
        VEHICLE_MODEL_DOCUMENT.engineType = FakeVehicleModel.VEHICLE_MODEL_ENGINE_TYPE.toString();
        VEHICLE_MODEL_DOCUMENT.rates = RATE_TABLE_DOCUMENT;
    }

    public static final VehicleDocument VEHICLE_DOCUMENT = new VehicleDocument();
    static {
        VEHICLE_DOCUMENT.id = new ObjectId(VEHICLE_ID);
        VEHICLE_DOCUMENT.licensePlate = LICENSE_PLATE;
        VEHICLE_DOCUMENT.vehicleModelId = FakeVehicleModel.VEHICLE_MODEL_ID;
        VEHICLE_DOCUMENT.position = new Point(new Position(LONGITUDE, LATITUDE));
        VEHICLE_DOCUMENT.autonomy = AUTONOMY;
        VEHICLE_DOCUMENT.lastStatusUpdate = UPDATED_AT.toEpochMilli();
        VEHICLE_DOCUMENT.currentState = VEHICLE_STATE.toString();
        VEHICLE_DOCUMENT.unlockCode = UNLOCK_CODE;
    }
}
