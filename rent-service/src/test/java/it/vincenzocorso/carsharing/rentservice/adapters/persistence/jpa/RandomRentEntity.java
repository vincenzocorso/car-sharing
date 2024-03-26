package it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import it.vincenzocorso.carsharing.rentservice.adapters.persistence.RentWrapper;
import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentState;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentStateTransition;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static it.vincenzocorso.carsharing.rentservice.domain.models.RandomRent.randomRent;
import static it.vincenzocorso.carsharing.rentservice.domain.models.RentState.*;
import static it.vincenzocorso.carsharing.rentservice.domain.models.RentState.STARTED;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomRentEntity {
    private static final EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();

    public static RentWrapper randomRentWrapper() {
        Rent rent = randomRent();
        Long version = enhancedRandom.nextObject(Long.class);
        return new RentWrapper(rent.getId(), rent.getDetails(), rent.getStateTransitions(), version);
    }

    public static RentEntity randomRentEntity() {
        RentState finalState = enhancedRandom.nextObject(RentState.class);

        RentEntity rentEntity = new RentEntity();
        rentEntity.setId(UUID.randomUUID());
        rentEntity.setCustomerId(enhancedRandom.nextObject(String.class));
        rentEntity.setVehicleId(enhancedRandom.nextObject(String.class));
        rentEntity.setCurrentState(finalState.toString());
        rentEntity.setVersion(enhancedRandom.nextObject(Long.class));
        rentEntity.setStateTransitions(randomRentStateTransitionEntityList(rentEntity, finalState));
        return rentEntity;
    }

    public static List<RentStateTransitionEntity> randomRentStateTransitionEntityList(RentEntity rentEntity, RentState finalState) {
        List<RentStateTransitionEntity> transitions = new ArrayList<>();
        int sequenceNumber = 1;

        RentStateTransitionEntity t = new RentStateTransitionEntity();
        t.setId(new RentStateTransitionId(rentEntity, sequenceNumber));
        t.setState(PENDING.toString());
        t.setTimestamp(getFutureInstant(sequenceNumber++));
        transitions.add(t);
        if(PENDING.equals(finalState)) {
            return transitions;
        }

        if(ACCEPTED.equals(finalState) || REJECTED.equals(finalState)) {
            t = new RentStateTransitionEntity();
            t.setId(new RentStateTransitionId(rentEntity, sequenceNumber));
            t.setState(finalState.toString());
            t.setTimestamp(getFutureInstant(sequenceNumber));
            transitions.add(t);
            return transitions;
        }
        t = new RentStateTransitionEntity();
        t.setId(new RentStateTransitionId(rentEntity, sequenceNumber));
        t.setState(ACCEPTED.toString());
        t.setTimestamp(getFutureInstant(sequenceNumber++));
        transitions.add(t);

        if(CANCELLED.equals(finalState)) {
            t = new RentStateTransitionEntity();
            t.setId(new RentStateTransitionId(rentEntity, sequenceNumber));
            t.setState(finalState.toString());
            t.setTimestamp(getFutureInstant(sequenceNumber));
            transitions.add(t);
            return transitions;
        }

        t = new RentStateTransitionEntity();
        t.setId(new RentStateTransitionId(rentEntity, sequenceNumber));
        t.setState(STARTED.toString());
        t.setTimestamp(getFutureInstant(sequenceNumber++));
        transitions.add(t);
        if(STARTED.equals(finalState)) {
            return transitions;
        }

        t = new RentStateTransitionEntity();
        t.setId(new RentStateTransitionId(rentEntity, sequenceNumber));
        t.setState(ENDED.toString());
        t.setTimestamp(getFutureInstant(sequenceNumber));
        transitions.add(t);

        return transitions;
    }

    private static LocalDateTime getFutureInstant(int sequenceNumber) {
        return LocalDateTime.ofInstant(Instant.now()
                .minus(10 + (long) (Math.random() * 3000), MINUTES)
                .plus(sequenceNumber, MINUTES), ZoneOffset.UTC);
    }

    public static void assertEqualsWithRent(Rent expected, RentEntity actual) {
        assertEquals(expected.getId(), actual.getId().toString());
        assertEquals(expected.getDetails().customerId(), actual.getCustomerId());
        assertEquals(expected.getDetails().vehicleId(), actual.getVehicleId());
        assertEqualsWithRentStateTransitionList(expected.getStateTransitions(), actual.getStateTransitions());
        if (expected instanceof RentWrapper r) {
            assertEquals(r.getVersion(), actual.getVersion());
        }
    }

    public static void assertEqualsWithRentStateTransitionList(List<RentStateTransition> expected, List<RentStateTransitionEntity> actual) {
        assertEquals(expected.size(), actual.size());
        for(int i = 0; i < expected.size(); i++) {
            RentStateTransition stateTransition = expected.get(i);
            RentStateTransitionEntity stateTransitionEntity = actual.get(i);

            assertEquals(stateTransition.getTimestamp(), stateTransitionEntity.getTimestamp().toInstant(ZoneOffset.UTC));
            assertEquals(stateTransition.getState().toString(), stateTransitionEntity.getState());
            assertEquals(stateTransition.getSequenceNumber(), stateTransitionEntity.getId().getSequenceNumber());
        }
    }

    public static void assertEqualsWithRentEntity(RentEntity expected, Rent actual) {
        assertEquals(expected.getId().toString(), actual.getId());
        assertEquals(expected.getCustomerId(), actual.getDetails().customerId());
        assertEquals(expected.getVehicleId(), actual.getDetails().vehicleId());
        assertEquals(expected.getCurrentState(), actual.getStateTransitions().get(actual.getStateTransitions().size() - 1).getState().toString());
        assertEquals(expected.getVersion(), actual instanceof RentWrapper r ? r.getVersion() : null);
        assertEqualsWithRentStateTransitionEntityList(expected.getStateTransitions(), actual.getStateTransitions());
    }

    public static void assertEqualsWithRentStateTransitionEntityList(List<RentStateTransitionEntity> expected, List<RentStateTransition> actual) {
        assertEquals(expected.size(), actual.size());
        for(int i = 0; i < expected.size(); i++) {
            RentStateTransitionEntity stateTransitionEntity = expected.get(i);
            RentStateTransition stateTransition = actual.get(i);

            assertEquals(stateTransitionEntity.getId().getSequenceNumber(), stateTransition.getSequenceNumber());
            assertEquals(stateTransitionEntity.getTimestamp(), LocalDateTime.ofInstant(stateTransition.getTimestamp(), ZoneOffset.UTC));
            assertEquals(stateTransitionEntity.getState(), stateTransition.getState().toString());
        }
    }
}
