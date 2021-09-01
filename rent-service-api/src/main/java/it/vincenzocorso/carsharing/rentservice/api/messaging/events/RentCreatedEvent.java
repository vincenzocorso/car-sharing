package it.vincenzocorso.carsharing.rentservice.api.messaging.events;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentCreatedEvent implements RentDomainEvent {
	public final String customerId;
	public final String vehicleId;

	@Override
	public String getType() {
		return "RENT_CREATED_EVENT";
	}
}
