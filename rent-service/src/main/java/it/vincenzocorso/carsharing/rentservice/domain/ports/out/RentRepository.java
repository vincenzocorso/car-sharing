package it.vincenzocorso.carsharing.rentservice.domain.ports.out;

import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.SearchRentCriteria;

import java.util.List;
import java.util.Optional;

public interface RentRepository {
	Optional<Rent> findById(String rentId);
	List<Rent> findByCriteria(SearchRentCriteria criteria);
	Rent save(Rent rent);
}
