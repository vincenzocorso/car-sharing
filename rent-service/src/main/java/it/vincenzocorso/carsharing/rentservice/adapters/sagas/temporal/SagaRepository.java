package it.vincenzocorso.carsharing.rentservice.adapters.sagas.temporal;

import it.vincenzocorso.carsharing.common.saga.Saga;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class SagaRepository {
	private final Map<String, Saga> sagasMap = new HashMap<>();

	@Autowired
	public SagaRepository(List<Saga> sagas) {
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
