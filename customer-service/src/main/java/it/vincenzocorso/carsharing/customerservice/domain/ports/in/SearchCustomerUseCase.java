package it.vincenzocorso.carsharing.customerservice.domain.ports.in;

import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import it.vincenzocorso.carsharing.customerservice.domain.models.SearchCustomerCriteria;

import java.util.List;

public interface SearchCustomerUseCase {
	Customer getCustomer(String customerId);
	List<Customer> getCustomers(SearchCustomerCriteria criteria);
}
