package it.vincenzocorso.carsharing.rentservice.domain.ports.in;

import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.SearchRentCriteria;

import java.util.List;

public interface SearchRentUseCase {
	List<Rent> getRents(SearchRentCriteria criteria);
	Rent getRent(String rentId);
}
