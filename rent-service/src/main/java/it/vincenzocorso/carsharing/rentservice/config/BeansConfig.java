package it.vincenzocorso.carsharing.rentservice.config;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEventProducer;
import it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa.RentJPARepositoryAdapter;
import it.vincenzocorso.carsharing.rentservice.domain.RentService;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {
	@Bean
	RentRepository rentRepository(RentJPARepositoryAdapter rentJPARepositoryAdapter) {
		return rentJPARepositoryAdapter;
	}

	@Bean
	RentService rentService(RentRepository rentRepository, DomainEventProducer domainEventProducer) {
		return new RentService(rentRepository, domainEventProducer);
	}
}
