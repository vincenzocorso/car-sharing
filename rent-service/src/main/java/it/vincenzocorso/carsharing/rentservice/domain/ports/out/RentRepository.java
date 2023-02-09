package it.vincenzocorso.carsharing.rentservice.domain.ports.out;

import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.SearchRentCriteria;

import java.util.List;
import java.util.Optional;

public interface RentRepository {
	/**
	 * Get the rent with the given id
	 * @param rentId The rent id
	 * @return The rent with the given id
	 */
	Optional<Rent> findById(String rentId);

	/**
	 * Get all the rents with the given criteria
	 * @param criteria The search criteria. Null attributes are not considered
	 * @return The list of all rents which respect these criteria
	 * @see SearchRentCriteria
	 */
	List<Rent> findByCriteria(SearchRentCriteria criteria);

	/**
	 * Save the given rent
	 * @param rent The rent to save
	 * @return The saved rent
	 */
	Rent save(Rent rent);
}
