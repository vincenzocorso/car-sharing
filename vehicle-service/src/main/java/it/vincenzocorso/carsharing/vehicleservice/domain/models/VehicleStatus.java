package it.vincenzocorso.carsharing.vehicleservice.domain.models;

import lombok.Builder;

import java.time.Instant;

@Builder
public record VehicleStatus(double latitude, double longitude, double autonomy, Instant updatedAt) {
}
