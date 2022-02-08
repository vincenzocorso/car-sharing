package it.vincenzocorso.carsharing.common.saga;

public interface Saga {
	String getName();
	SagaDefinition getDefinition();
}
