package it.vincenzocorso.carsharing.vehicleservice.adapters.persistence.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import it.vincenzocorso.carsharing.vehicleservice.domain.exceptions.VehicleNotFoundException;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.SearchVehicleCriteria;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.Vehicle;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.VehicleState;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.out.VehicleRepository;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.nearSphere;

@ApplicationScoped
public class VehicleRepositoryAdapter implements VehicleRepository {
    @Inject
    VehicleDocumentMapper vehicleDocumentMapper;

    @Override
    public Optional<Vehicle> findById(String vehicleId) {
        ObjectId documentId = this.convertToDocumentId(vehicleId);
        Optional<VehicleDocument> vehicleDocument = VehicleDocument.findByIdOptional(documentId);
        return vehicleDocument.map(this.vehicleDocumentMapper::convertFromDocument);
    }

    private ObjectId convertToDocumentId(String vehicleId) {
        try {
            return new ObjectId(vehicleId);
        } catch (IllegalArgumentException e) {
            throw new VehicleNotFoundException(vehicleId);
        }
    }

    @Override
    public List<Vehicle> findByCriteria(SearchVehicleCriteria criteria) {
        MongoCollection<VehicleDocument> vehiclesCollection = VehicleDocument.mongoCollection();
        Bson query = Filters.empty();

        if(criteria.latitude != null && criteria.longitude != null && criteria.radius != null) {
            var center = new Point(new Position(criteria.longitude, criteria.latitude));
            query = Filters.and(query, nearSphere("position", center, criteria.radius, 0.0));
        }

        if(criteria.states != null && !criteria.states.isEmpty())
            query = Filters.and(Filters.in("currentState", VehicleState.values()));

        var iterable = vehiclesCollection.find(query);
        if(criteria.offset != null && criteria.limit != null)
            iterable = iterable.skip(criteria.offset).limit(criteria.limit);

        return iterable
                .into(new ArrayList<>())
                .stream()
                .map(this.vehicleDocumentMapper::convertFromDocument)
                .toList();
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        VehicleDocument vehicleDocument = this.vehicleDocumentMapper.convertToDocument(vehicle);
        vehicleDocument.persistOrUpdate();
        return this.vehicleDocumentMapper.convertFromDocument(vehicleDocument);
    }
}
