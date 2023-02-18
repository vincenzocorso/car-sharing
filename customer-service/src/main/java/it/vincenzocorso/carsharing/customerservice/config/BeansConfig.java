package it.vincenzocorso.carsharing.customerservice.config;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEventProducer;
import it.vincenzocorso.carsharing.customerservice.adapters.web.ValidationExceptionHandler;
import it.vincenzocorso.carsharing.customerservice.domain.CustomerService;
import it.vincenzocorso.carsharing.customerservice.domain.ports.out.CustomerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class BeansConfig {
	@Produces @ApplicationScoped
	CustomerService customerService(CustomerRepository customerRepository, DomainEventProducer domainEventProducer) {
		return new CustomerService(customerRepository, domainEventProducer);
	}

	@Produces @ApplicationScoped
	ValidationExceptionHandler validationExceptionHandler() {
		return new ValidationExceptionHandler();
	}
}
