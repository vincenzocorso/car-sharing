package it.vincenzocorso.carsharing.rentservice.domain.ports.out;

import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;

import java.util.List;
import java.util.Optional;

public interface RentRepository {
	Optional<Rent> findById(String rentId);
	List<Rent> findByCustomer(String customerId);
	Rent save(Rent rent);
}
