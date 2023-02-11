package it.vincenzocorso.carsharing.customerservice.domain.ports.out;

import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import it.vincenzocorso.carsharing.customerservice.domain.models.SearchCustomerCriteria;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
	/**
	 * Saves the given customer
	 * @param customer The customer to save
	 * @return The saved customer
	 */
	Customer save(Customer customer);

	/**
	 * Gets the customer with the given id
	 * @param customerId The customer id
	 * @return The customer with the given id
	 */
	Optional<Customer> findById(String customerId);

	/**
	 * Gets all the customers with the given criteria
	 * @param criteria The search criteria. Null fields are not considered
	 * @return The list of all customers which respect these criteria
	 * @see SearchCustomerCriteria
	 */
	List<Customer> findByCriteria(SearchCustomerCriteria criteria);
}
