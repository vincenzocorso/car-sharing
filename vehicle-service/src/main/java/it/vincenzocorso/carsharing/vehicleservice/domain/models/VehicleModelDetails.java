package it.vincenzocorso.carsharing.vehicleservice.domain.models;

import lombok.Builder;

@Builder
public record VehicleModelDetails(
    String name,
    Integer seats,
    TransmissionType transmissionType,
    EngineType engineType
) {}
