package it.vincenzocorso.carsharing.vehicleservice.adapters.web;

import it.vincenzocorso.carsharing.vehicleservice.domain.models.SearchVehicleCriteria;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.Vehicle;
import it.vincenzocorso.carsharing.vehicleservice.domain.models.VehicleState;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.in.RentVehicleUseCase;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.in.SearchVehicleUseCase;
import it.vincenzocorso.carsharing.vehicleservice.domain.ports.in.UpdateVehicleStatusUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.OK;

@ApplicationScoped
@AllArgsConstructor
public class VehicleController implements VehicleRestApi {
    RentVehicleUseCase rentVehicleUseCase;
    SearchVehicleUseCase searchVehicleUseCase;
    UpdateVehicleStatusUseCase updateVehicleStatusUseCase;
    VehicleMapper vehicleMapper;
    VehicleModelMapper vehicleModelMapper;

    @Override
    public Response getVehicles(SearchVehicleQueryParams queryParams) {
        SearchVehicleCriteria criteria = SearchVehicleCriteria.builder()
                .latitude(queryParams.latitude)
                .longitude(queryParams.longitude)
                .radius(queryParams.radius)
                .states(queryParams.states.stream().map(VehicleState::valueOf).toList())
                .limit(queryParams.limit)
                .offset(queryParams.offset)
                .build();

        List<VehicleResponse> responseBody = this.searchVehicleUseCase.getVehicles(criteria).stream()
                .map(this.vehicleMapper::convertToDto)
                .toList();

        return Response.status(OK).entity(responseBody).build();
    }

    @Override
    public Response getVehicleModels() {
        List<VehicleModelResponse> responseBody = this.searchVehicleUseCase.getVehicleModels().stream()
                .map(this.vehicleModelMapper::convertToDto)
                .toList();

        return Response.status(OK).entity(responseBody).build();
    }

    @Override
    public Response getVehicle(String vehicleId) {
        Vehicle retrievedVehicle = this.searchVehicleUseCase.getVehicle(vehicleId);
        VehicleResponse responseBody = this.vehicleMapper.convertToDto(retrievedVehicle);
        return Response.status(OK).entity(responseBody).build();
    }
}
