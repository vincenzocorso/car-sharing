package it.vincenzocorso.carsharing.rentservice.domain.ports.in;

import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.SearchRentCriteria;

import java.util.List;

public interface SearchRentUseCase {
	/**
	 * Gets all the rents with the given criteria
	 * @param criteria The search criteria. Null attributes are not considered
	 * @return The list of all rents which respect these criteria
	 * @see SearchRentCriteria
	 */
	List<Rent> getRents(SearchRentCriteria criteria);

	/**
	 * Gets the rent with the given id
	 * @param rentId The rent id
	 * @return The rent with the given id
	 * @throws it.vincenzocorso.carsharing.rentservice.domain.exceptions.RentNotFoundException If the rent with the given id does not exist
	 */
	Rent getRent(String rentId);
}
