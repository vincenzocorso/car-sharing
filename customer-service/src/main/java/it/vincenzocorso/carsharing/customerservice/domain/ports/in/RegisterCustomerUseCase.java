package it.vincenzocorso.carsharing.customerservice.domain.ports.in;

import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import it.vincenzocorso.carsharing.customerservice.domain.models.CustomerDetails;

public interface RegisterCustomerUseCase {
	Customer registerCustomer(CustomerDetails customerDetails);
}
