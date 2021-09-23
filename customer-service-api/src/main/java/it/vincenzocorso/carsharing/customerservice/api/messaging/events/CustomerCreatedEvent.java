package it.vincenzocorso.carsharing.customerservice.api.messaging.events;

import lombok.Builder;

@Builder
public class CustomerCreatedEvent implements CustomerDomainEvent {
	public final String firstName;
	public final String lastName;
	public final String dateOfBirth;
	public final String fiscalCode;
	public final String email;
	public final String phoneNumber;

	@Override
	public String getType() {
		return "CUSTOMER_CREATED_EVENT";
	}
}
