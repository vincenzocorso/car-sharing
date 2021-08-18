package it.vincenzocorso.carsharing.rentservice.domain.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class RentDetails {
	private final String customerId;
	private final String vehicleId;
}
