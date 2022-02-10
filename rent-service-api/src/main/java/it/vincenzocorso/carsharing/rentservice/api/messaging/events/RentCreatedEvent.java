package it.vincenzocorso.carsharing.rentservice.api.messaging.events;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentCreatedEvent implements DomainEvent {
	public final String customerId;
	public final String vehicleId;

	@Override
	public String getType() {
		return "RENT_CREATED_EVENT";
	}
}
