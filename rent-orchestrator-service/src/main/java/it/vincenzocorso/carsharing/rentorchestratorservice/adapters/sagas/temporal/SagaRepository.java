package it.vincenzocorso.carsharing.rentorchestratorservice.adapters.sagas.temporal;

import it.vincenzocorso.carsharing.common.saga.Saga;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
@Slf4j
public class SagaRepository {
	private final Map<String, Saga> sagasMap = new HashMap<>();

	public SagaRepository(@Any Instance<Saga> sagas) {
		sagas.forEach(saga -> this.sagasMap.put(saga.getName(), saga));
	}

	public Saga getSaga(String sagaName) {
		Saga saga = this.sagasMap.get(sagaName);

		if(saga == null) {
			String message = "Saga not found: " + sagaName;
			log.error(message);
			throw new IllegalArgumentException(message);
		}

		return saga;
	}
}
