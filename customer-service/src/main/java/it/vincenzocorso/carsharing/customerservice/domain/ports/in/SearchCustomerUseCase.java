package it.vincenzocorso.carsharing.customerservice.domain.ports.in;

import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import it.vincenzocorso.carsharing.customerservice.domain.models.SearchCustomerCriteria;

import java.util.List;

public interface SearchCustomerUseCase {
	/**
	 * Gets the customer with the given id
	 * @param customerId The customer id
	 * @return The customer with the given id
	 * @throws it.vincenzocorso.carsharing.customerservice.domain.exceptions.CustomerNotFoundException If the customer with the given id does not exist
	 */
	Customer getCustomer(String customerId);

	/**
	 * Gets all the customers with the given criteria
	 * @param criteria The search criteria. Null fields are not considered
	 * @return The customers that match the given criteria
	 * @see SearchCustomerCriteria
	 */
	List<Customer> getCustomers(SearchCustomerCriteria criteria);
}
