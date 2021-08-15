package it.vincenzocorso.carsharing.rentservice.domain.ports.in;

import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;

import java.util.List;

public interface SearchRentUseCase {
	Rent getCustomerRentById(String customerId, String rentId);
	List<Rent> getAllCustomerRents(String customerId);
}
