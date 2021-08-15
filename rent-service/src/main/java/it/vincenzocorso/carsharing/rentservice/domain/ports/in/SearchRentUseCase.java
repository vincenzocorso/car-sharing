package it.vincenzocorso.carsharing.rentservice.domain.ports.in;

import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;

import java.util.List;

public interface SearchRentUseCase {
	Rent getCustomerRent(String customerId, String rentId);
	List<Rent> getCustomerRents(String customerId);
}
