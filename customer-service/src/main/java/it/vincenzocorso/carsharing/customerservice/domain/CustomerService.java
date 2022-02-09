package it.vincenzocorso.carsharing.customerservice.domain;

import it.vincenzocorso.carsharing.common.messaging.events.ResultWithEvents;
import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import it.vincenzocorso.carsharing.customerservice.domain.models.CustomerDetails;
import it.vincenzocorso.carsharing.customerservice.domain.models.SearchCustomerCriteria;
import it.vincenzocorso.carsharing.customerservice.domain.ports.in.RegisterCustomerUseCase;
import it.vincenzocorso.carsharing.customerservice.domain.ports.in.SearchCustomerUseCase;
import it.vincenzocorso.carsharing.customerservice.domain.ports.out.CustomerRepository;
import it.vincenzocorso.carsharing.customerservice.domain.exceptions.CustomerNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CustomerService implements RegisterCustomerUseCase, SearchCustomerUseCase {
	private final CustomerRepository customerRepository;

	@Override
	public Customer registerCustomer(CustomerDetails customerDetails) {
		ResultWithEvents<Customer> resultWithEvents = Customer.create(customerDetails);

		Customer savedCustomer = this.customerRepository.save(resultWithEvents.result);

		// TODO: publish events

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
}