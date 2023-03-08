package it.vincenzocorso.carsharing.vehicleservice.adapters.persistence.mongodb;

import com.mongodb.client.model.geojson.Point;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(collection = "vehicles")
public class VehicleDocument extends PanacheMongoEntityBase {
    @BsonId
    public ObjectId id;
    public String licensePlate;
    public String vehicleModelId;
    public Point position;
    public double autonomy;
    public Long lastStatusUpdate;
    public String currentState;
    public String unlockCode;
}
