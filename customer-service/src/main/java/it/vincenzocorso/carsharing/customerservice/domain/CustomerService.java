package it.vincenzocorso.carsharing.customerservice.domain;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEventProducer;
import it.vincenzocorso.carsharing.common.messaging.events.ResultWithEvents;
import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import it.vincenzocorso.carsharing.customerservice.domain.models.CustomerDetails;
import it.vincenzocorso.carsharing.customerservice.domain.models.SearchCustomerCriteria;
import it.vincenzocorso.carsharing.customerservice.domain.ports.in.RegisterCustomer;
import it.vincenzocorso.carsharing.customerservice.domain.ports.in.SearchCustomer;
import it.vincenzocorso.carsharing.customerservice.domain.ports.in.RentVehicle;
import it.vincenzocorso.carsharing.customerservice.domain.ports.out.CustomerRepository;
import it.vincenzocorso.carsharing.customerservice.domain.exceptions.CustomerNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CustomerService implements RegisterCustomer, SearchCustomer, RentVehicle {
	public static final String EVENTS_CHANNEL = "customer-service-events";
	private final CustomerRepository customerRepository;
	private final DomainEventProducer domainEventProducer;

	@Override
	public Customer registerCustomer(CustomerDetails customerDetails) {
		ResultWithEvents<Customer> resultWithEvents = Customer.create(customerDetails);

		Customer savedCustomer = this.customerRepository.save(resultWithEvents.result);

		this.domainEventProducer.publish(EVENTS_CHANNEL, savedCustomer.getId(), resultWithEvents.events);

		return savedCustomer;
	}

	@Override
	public Customer getCustomer(String customerId) {
		return this.customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
	}

	@Override
	public List<Customer> getCustomers(SearchCustomerCriteria criteria) {
		return this.customerRepository.findByCriteria(criteria);
	}

	@Override
	public boolean verifyCustomer(String customerId) {
		Customer savedCustomer = this.customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

		return savedCustomer.canRentAVehicle();
	}
}
