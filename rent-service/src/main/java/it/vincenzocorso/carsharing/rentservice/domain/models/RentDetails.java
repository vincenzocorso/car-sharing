package it.vincenzocorso.carsharing.rentservice.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RentDetails {
	private final String customerId;
	private final String vehicleId;
}
