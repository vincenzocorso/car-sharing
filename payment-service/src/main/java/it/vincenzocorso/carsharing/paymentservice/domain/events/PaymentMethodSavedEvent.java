package it.vincenzocorso.carsharing.paymentservice.domain.events;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;
import lombok.Builder;

@Builder
public record PaymentMethodSavedEvent(
    String customerId
) implements DomainEvent {
    @Override
    public String getType() {
        return "PAYMENT_METHOD_SAVED_EVENT";
    }
}
