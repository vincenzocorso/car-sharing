package it.vincenzocorso.carsharing.paymentservice.domain.events;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;

public record PaymentMethodStateTransitionEvent(String oldState, String newState) implements DomainEvent {
	@Override
	public String getType() {
		return "PAYMENT_METHOD_STATE_TRANSITION_EVENT";
	}
}
