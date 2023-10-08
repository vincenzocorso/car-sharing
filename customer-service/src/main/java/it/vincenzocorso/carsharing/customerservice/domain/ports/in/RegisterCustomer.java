package it.vincenzocorso.carsharing.customerservice.domain.ports.in;

import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import it.vincenzocorso.carsharing.customerservice.domain.models.CustomerDetails;

public interface RegisterCustomer {
	/**
	 * Registers a new customer
	 * @param customerDetails The customer details
	 * @return The registered customer
	 * @see CustomerDetails
	 */
	Customer registerCustomer(CustomerDetails customerDetails);
}
