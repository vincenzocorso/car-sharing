package it.vincenzocorso.carsharing.vehicleservice.adapters.web;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/vehicles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface VehicleRestApi {
    @GET
    Response getVehicles(@Valid @BeanParam SearchVehicleQueryParams queryParams);

    @GET
    @Path("/models")
    Response getVehicleModels();

    @GET
    @Path("{vehicleId}")
    Response getVehicle(@PathParam("vehicleId") String vehicleId);
}
