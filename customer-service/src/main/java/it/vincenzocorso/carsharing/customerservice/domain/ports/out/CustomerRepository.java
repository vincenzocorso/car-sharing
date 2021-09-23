package it.vincenzocorso.carsharing.customerservice.domain.ports.out;

import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import it.vincenzocorso.carsharing.customerservice.domain.models.SearchCustomerCriteria;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
	Customer save(Customer customer);
	Optional<Customer> findById(String customerId);
	List<Customer> findByCriteria(SearchCustomerCriteria criteria);
}
