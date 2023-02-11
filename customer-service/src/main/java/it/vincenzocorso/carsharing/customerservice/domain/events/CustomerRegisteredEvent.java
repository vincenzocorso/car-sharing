package it.vincenzocorso.carsharing.customerservice.domain.events;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;
import lombok.Builder;

@Builder
public record CustomerRegisteredEvent(
		String firstName,
		String lastName,
		String dateOfBirth,
		String fiscalCode,
		String email,
		String phoneNumber
) implements DomainEvent {
	@Override
	public String getType() {
		return "CUSTOMER_REGISTERED_EVENT";
	}
}
