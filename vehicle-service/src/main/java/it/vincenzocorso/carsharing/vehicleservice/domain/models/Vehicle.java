package it.vincenzocorso.carsharing.vehicleservice.domain.models;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;
import it.vincenzocorso.carsharing.vehicleservice.domain.events.VehicleStateTransitionEvent;
import it.vincenzocorso.carsharing.vehicleservice.domain.exceptions.IllegalVehicleStateTransitionException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Getter
public class Vehicle {
    private String id;
    private final String licensePlate;
    private final VehicleModel model;
    private VehicleStatus status;
    private VehicleState currentState;
    private String unlockCode;

    /**
     * Marks the vehicle as booked
     * @throws IllegalVehicleStateTransitionException If the vehicle cannot be booked
     */
    public List<DomainEvent> book() {
        VehicleState state = this.currentState;
        if(!state.equals(VehicleState.AVAILABLE))
            throw new IllegalVehicleStateTransitionException("The vehicle is not available");

        this.generateNewUnlockCode();
        this.currentState = VehicleState.BOOKED;

        return List.of(new VehicleStateTransitionEvent(state.toString(), this.currentState.toString()));
    }

    /**
     * Generates a new unlock code for the vehicle.
     * The unlock code is an alphanumeric string of 4 characters
     */
    private void generateNewUnlockCode() {
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 1; i <= 4; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            sb.append(allowedChars.charAt(randomIndex));
        }
        this.unlockCode = sb.toString();
    }

    public void updateStatus(VehicleStatus status) {
        this.status = status;
    }
}
