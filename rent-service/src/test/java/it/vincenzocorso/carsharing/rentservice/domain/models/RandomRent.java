package it.vincenzocorso.carsharing.rentservice.domain.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static it.vincenzocorso.carsharing.rentservice.domain.models.RentState.*;
import static java.time.temporal.ChronoUnit.MINUTES;

public class RandomRent {
    public static Rent randomRent() {
        return randomRent(ENDED);
    }

    public static Rent randomRent(RentState finalState) {
        String rentId = UUID.randomUUID().toString();
        RentDetails rentDetails = randomRentDetails();
        List<RentStateTransition> rentStateTransitions = randomRentStateTransitionList(finalState);
        return new Rent(rentId, rentDetails, rentStateTransitions);
    }

    public static RentDetails randomRentDetails() {
        String customerId = UUID.randomUUID().toString();
        String vehicleId = UUID.randomUUID().toString();
        return new RentDetails(customerId, vehicleId);
    }

    public static RentRegistry randomRentRegistry(RentState finalState) {
        List<RentStateTransition> rentStateTransitions = randomRentStateTransitionList(finalState);
        return new RentRegistry(rentStateTransitions);
    }

    public static List<RentStateTransition> randomRentStateTransitionList(RentState finalState) {
        List<RentStateTransition> transitions = new ArrayList<>();
        int sequenceNumber = 1;

        transitions.add(new RentStateTransition(getFutureInstant(sequenceNumber), PENDING, sequenceNumber++));
        if(PENDING.equals(finalState)) {
            return transitions;
        }

        if(ACCEPTED.equals(finalState) || REJECTED.equals(finalState)) {
            transitions.add(new RentStateTransition(getFutureInstant(sequenceNumber), finalState, sequenceNumber));
            return transitions;
        }
        transitions.add(new RentStateTransition(getFutureInstant(sequenceNumber), ACCEPTED, sequenceNumber++));

        if(CANCELLED.equals(finalState)) {
            transitions.add(new RentStateTransition(getFutureInstant(sequenceNumber), finalState, sequenceNumber));
            return transitions;
        }

        transitions.add(new RentStateTransition(getFutureInstant(sequenceNumber), RentState.STARTED, sequenceNumber++));
        if(STARTED.equals(finalState)) {
            return transitions;
        }

        transitions.add(new RentStateTransition(getFutureInstant(sequenceNumber), RentState.ENDED, sequenceNumber));
        return transitions;
    }

    private static Instant getFutureInstant(int sequenceNumber) {
        return Instant.now()
                .minus(10 + (long) (Math.random() * 3000), MINUTES)
                .plus(sequenceNumber, MINUTES);
    }
}
