package it.vincenzocorso.carsharing.common.saga;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SagaDefinition {
	private final List<SagaStep> steps;
}
