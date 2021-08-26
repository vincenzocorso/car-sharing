package it.vincenzocorso.carsharing.common.saga;

import lombok.Getter;

import java.util.UUID;

@Getter
public class SagaState {
	private final String id;

	public SagaState() {
		this.id = UUID.randomUUID().toString();
	}
}
