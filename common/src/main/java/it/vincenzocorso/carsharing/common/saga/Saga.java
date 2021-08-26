package it.vincenzocorso.carsharing.common.saga;

public interface Saga<S extends SagaState> {
	String getName();
	SagaDefinition<S> getDefinition();
}
