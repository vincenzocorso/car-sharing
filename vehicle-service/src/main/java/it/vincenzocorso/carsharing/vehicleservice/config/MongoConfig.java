package it.vincenzocorso.carsharing.vehicleservice.config;

import com.mongodb.client.model.Indexes;
import io.quarkus.runtime.StartupEvent;
import it.vincenzocorso.carsharing.vehicleservice.adapters.persistence.mongodb.VehicleDocument;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class MongoConfig {
    void createIndexes(@Observes StartupEvent ev) {
        VehicleDocument.mongoCollection().createIndex(Indexes.geo2dsphere("position"));
    }
}
