package it.vincenzocorso.carsharing.rentservice.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RentDetails {
	/**
	 * The id of the customer who created the rent
	 */
	private final String customerId;

	/**
	 * The id of the vehicle involved in the rent
	 */
	private final String vehicleId;
}
