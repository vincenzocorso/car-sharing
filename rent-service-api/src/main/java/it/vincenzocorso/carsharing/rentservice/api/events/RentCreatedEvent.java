package it.vincenzocorso.carsharing.rentservice.api.events;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentCreatedEvent implements RentDomainEvent {
	public final String customerId;
	public final String vehicleId;
}
