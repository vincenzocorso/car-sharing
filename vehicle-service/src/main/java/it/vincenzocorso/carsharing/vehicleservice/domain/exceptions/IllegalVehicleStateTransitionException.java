package it.vincenzocorso.carsharing.vehicleservice.domain.exceptions;

import lombok.Getter;

@Getter
public class IllegalVehicleStateTransitionException extends RuntimeException {
    private final String reason;

    public IllegalVehicleStateTransitionException(String reason) {
        super(reason);
        this.reason = reason;
    }
}
