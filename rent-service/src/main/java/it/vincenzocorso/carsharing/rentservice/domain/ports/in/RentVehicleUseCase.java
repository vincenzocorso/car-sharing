package it.vincenzocorso.carsharing.rentservice.domain.ports.in;

import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;

public interface RentVehicleUseCase {
	/**
	 * Creates a new rent
	 * @param customerId The id of the customer that started the rent
	 * @param vehicleId The id of the vehicle rented
	 * @return The rent that was created
	 */
	Rent createRent(String customerId, String vehicleId);

	/**
	 * Rejects a rent
	 * @param rentId The rent id
	 * @return The rent with the given id
	 */
	Rent rejectRent(String rentId);

	/**
	 * Cancels a rent
	 * @param rentId The rent id
	 * @return The rent with the given id
	 */
	Rent cancelRent(String rentId);

	/**
	 * Starts a rent
	 * @param rentId The rent id
	 * @return The rent with the given id
	 */
	Rent startRent(String rentId);

	/**
	 * Ends a rent
	 * @param rentId The rent id
	 * @return The rent with the given id
	 */
	Rent endRent(String rentId);
}
