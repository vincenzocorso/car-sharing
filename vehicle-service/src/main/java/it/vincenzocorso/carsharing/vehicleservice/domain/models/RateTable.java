package it.vincenzocorso.carsharing.vehicleservice.domain.models;

import lombok.Builder;

@Builder
public record RateTable(double ratePerMinute, double ratePerHour, double ratePerDay, double ratePerKilometer) {
}
