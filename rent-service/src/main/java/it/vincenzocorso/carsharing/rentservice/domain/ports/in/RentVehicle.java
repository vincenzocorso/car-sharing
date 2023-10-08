package it.vincenzocorso.carsharing.rentservice.domain.ports.in;

import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;

public interface RentVehicle {
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
	 * @throws it.vincenzocorso.carsharing.rentservice.domain.exceptions.RentNotFoundException If the rent with the given id does not exist
	 * @throws it.vincenzocorso.carsharing.rentservice.domain.exceptions.IllegalRentStateTransitionException If the rent cannot be rejected
	 */
	Rent rejectRent(String rentId);

	/**
	 * Accept a rent
	 * @param rentId The rent id
	 * @return The rent with the given id
	 * @throws it.vincenzocorso.carsharing.rentservice.domain.exceptions.RentNotFoundException If the rent with the given id does not exist
	 * @throws it.vincenzocorso.carsharing.rentservice.domain.exceptions.IllegalRentStateTransitionException If the rent cannot be accepted
	 */
	Rent acceptRent(String rentId);

	/**
	 * Cancels a rent
	 * @param rentId The rent id
	 * @return The rent with the given id
	 * @throws it.vincenzocorso.carsharing.rentservice.domain.exceptions.RentNotFoundException If the rent with the given id does not exist
	 * @throws it.vincenzocorso.carsharing.rentservice.domain.exceptions.IllegalRentStateTransitionException If the rent cannot be cancelled
	 */
	Rent cancelRent(String rentId);

	/**
	 * Starts a rent
	 * @param rentId The rent id
	 * @return The rent with the given id
	 * @throws it.vincenzocorso.carsharing.rentservice.domain.exceptions.RentNotFoundException If the rent with the given id does not exist
	 * @throws it.vincenzocorso.carsharing.rentservice.domain.exceptions.IllegalRentStateTransitionException If the rent cannot be started
	 */
	Rent startRent(String rentId);

	/**
	 * Ends a rent
	 * @param rentId The rent id
	 * @return The rent with the given id
	 * @throws it.vincenzocorso.carsharing.rentservice.domain.exceptions.RentNotFoundException If the rent with the given id does not exist
	 * @throws it.vincenzocorso.carsharing.rentservice.domain.exceptions.IllegalRentStateTransitionException If the rent cannot be ended
	 */
	Rent endRent(String rentId);
}
