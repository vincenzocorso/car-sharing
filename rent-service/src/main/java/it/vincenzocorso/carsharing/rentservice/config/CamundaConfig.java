package it.vincenzocorso.carsharing.rentservice.config;

import it.vincenzocorso.carsharing.common.saga.Saga;
import it.vincenzocorso.carsharing.common.saga.SagaState;
import it.vincenzocorso.carsharing.rentservice.adapters.sagas.manager.CamundaSagaManagerAdapter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@AllArgsConstructor
public class CamundaConfig {
	private CamundaSagaManagerAdapter camundaSagaManager;
	private List<Saga<? extends SagaState>> sagas;

	@PostConstruct
	void registerSagas() {
		for(Saga<? extends SagaState> saga : this.sagas)
			this.camundaSagaManager.registerSaga(saga);
	}
}
