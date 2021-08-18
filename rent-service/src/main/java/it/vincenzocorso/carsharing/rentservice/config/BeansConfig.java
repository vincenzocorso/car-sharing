package it.vincenzocorso.carsharing.rentservice.config;

import it.vincenzocorso.carsharing.rentservice.domain.RentService;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.RentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {
	@Bean
	public RentRepository rentRepository() {
		return null;
	}

	@Bean
	public RentService rentService() {
		return new RentService(this.rentRepository());
	}
}
