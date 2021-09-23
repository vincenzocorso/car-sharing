package it.vincenzocorso.carsharing.rentservice.adapters.sagas.manager;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Slf4j
public class EmptyStepExecutor implements JavaDelegate {
	@Override
	public void execute(DelegateExecution execution) {
	}
}
