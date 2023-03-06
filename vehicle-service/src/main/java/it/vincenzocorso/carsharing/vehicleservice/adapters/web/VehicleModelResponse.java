package it.vincenzocorso.carsharing.vehicleservice.adapters.web;

import lombok.Builder;

@Builder
public record VehicleModelResponse(
    String vehicleModelId,
    String name,
    Integer seats,
    String transmission,
    String engine,
    RateTableResponse rates
) {}

@Builder
record RateTableResponse(
    Double ratePerMinute,
    Double ratePerHour,
    Double ratePerDay,
    Double ratePerKilometer
) {}
