package it.vincenzocorso.carsharing.vehicleservice.adapters.web;

import lombok.Builder;

@Builder
public record VehicleResponse(
    String vehicleId,
    String licensePlate,
    VehiclePosition position,
    Double autonomy,
    String currentState,
    String vehicleModelId
) {}

record VehiclePosition(
    Double latitude,
    Double longitude
) {}
