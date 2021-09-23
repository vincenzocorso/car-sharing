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
	BookVehicleProxy bookVehicleProxy() {
		return new BookVehicleProxy() {
			@Override
			public void bookVehicle(String vehicleId) {
				log.info("Sending " + this.getMessageType() + " " + vehicleId);
			}

			@Override
			public String getMessageType() {
				return "BOOK_VEHICLE_COMMAND";
			}

			@Override
			public String getResponseMessageType() {
				return "BOOK_VEHICLE_REPLY";
			}
		};
	}

	@Bean
	ApproveRentProxy approveRentProxy() {
		return new ApproveRentProxy() {
			@Override
			public void approveRent(String rentId) {
				log.info("Sending " + this.getMessageType() + " " + rentId);
			}

			@Override
			public String getMessageType() {
				return "APPROVE_RENT_COMMAND";
			}

			@Override
			public String getResponseMessageType() {
				return "APPROVE_RENT_REPLY";
			}
		};
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
	RentService rentService(RentRepository rentRepository, SagaManager sagaManager, CreateRentSaga createRentSaga) {
		return new RentService(rentRepository, sagaManager, createRentSaga);
	}
}
