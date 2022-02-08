package it.vincenzocorso.carsharing.rentservice.config;

import it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa.RentJPARepositoryAdapter;
import it.vincenzocorso.carsharing.rentservice.domain.RentService;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.*;
import it.vincenzocorso.carsharing.rentservice.domain.sagas.CreateRentSaga;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class BeansConfig {
	@Bean
	RentRepository rentRepository(RentJPARepositoryAdapter rentJPARepositoryAdapter) {
		return rentJPARepositoryAdapter;
	}

	@Bean
	CreateRentSaga createRentSaga(
			RejectRentProxy rejectRentProxy,
			VerifyCustomerProxy verifyCustomerProxy,
			BookVehicleProxy bookVehicleProxy,
			ApproveRentProxy approveRentProxy) {
		return new CreateRentSaga(rejectRentProxy, verifyCustomerProxy, bookVehicleProxy, approveRentProxy);
	}

	@Bean
	RentService rentService(RentRepository rentRepository, RentSagaManager rentSagaManager, CreateRentSaga createRentSaga) {
		return new RentService(rentRepository, rentSagaManager, createRentSaga);
	}
}
