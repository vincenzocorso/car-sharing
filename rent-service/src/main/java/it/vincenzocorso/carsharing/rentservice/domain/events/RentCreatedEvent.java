package it.vincenzocorso.carsharing.rentservice.domain.events;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;

public record RentCreatedEvent(String customerId, String vehicleId) implements DomainEvent {
	@Override
	public String getType() {
		return "RENT_CREATED_EVENT";
	}
}
