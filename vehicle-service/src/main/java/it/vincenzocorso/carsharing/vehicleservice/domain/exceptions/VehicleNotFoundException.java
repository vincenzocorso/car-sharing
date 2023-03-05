package it.vincenzocorso.carsharing.vehicleservice.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VehicleNotFoundException extends RuntimeException {
    private final String vehicleId;
}
