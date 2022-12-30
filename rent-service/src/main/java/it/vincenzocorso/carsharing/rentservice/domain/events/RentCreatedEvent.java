package it.vincenzocorso.carsharing.rentservice.domain.events;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class RentCreatedEvent implements DomainEvent {
	public final String customerId;
	public final String vehicleId;

	@Override
	public String getType() {
		return "RENT_CREATED_EVENT";
	}
}
