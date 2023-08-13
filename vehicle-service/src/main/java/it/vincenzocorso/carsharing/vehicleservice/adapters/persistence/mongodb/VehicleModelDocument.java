package it.vincenzocorso.carsharing.vehicleservice.adapters.persistence.mongodb;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(collection = "vehicle_models")
public class VehicleModelDocument extends PanacheMongoEntityBase {
    @BsonId
    public ObjectId id;
    public String name;
    public Integer seats;
    public String transmissionType;
    public String engineType;
    public RateTableDocument rates;
}
