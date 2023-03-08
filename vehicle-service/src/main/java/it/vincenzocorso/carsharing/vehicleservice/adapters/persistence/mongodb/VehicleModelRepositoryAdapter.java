package it.vincenzocorso.carsharing.vehicleservice.adapters.persistence.mongodb;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.VehicleModel;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.out.VehicleModelRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class VehicleModelRepositoryAdapter implements VehicleModelRepository {
    @Inject
    VehicleModelDocumentMapper vehicleModelDocumentMapper;

    @Override
    public List<VehicleModel> findAll() {
        return VehicleModelDocument.<VehicleModelDocument>listAll()
                .stream()
                .map(this.vehicleModelDocumentMapper::convertFromDocument)
                .toList();
    }
}
