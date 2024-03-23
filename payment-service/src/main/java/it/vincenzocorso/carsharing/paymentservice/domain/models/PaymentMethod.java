package it.vincenzocorso.carsharing.paymentservice.domain.models;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;
import it.vincenzocorso.carsharing.common.messaging.events.ResultWithEvents;
import it.vincenzocorso.carsharing.paymentservice.domain.events.PaymentMethodSavedEvent;
import it.vincenzocorso.carsharing.paymentservice.domain.events.PaymentMethodStateTransitionEvent;
import it.vincenzocorso.carsharing.paymentservice.domain.exceptions.IllegalPaymentMethodStateTransitionException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethodState.*;

@AllArgsConstructor
@Getter
public class PaymentMethod {
    private String id;
    private final PaymentMethodDetails paymentMethodDetails;
    private PaymentMethodState state;
    private Map<String, String> metadata;

    private PaymentMethod(PaymentMethodDetails paymentMethodDetails) {
        this.paymentMethodDetails = paymentMethodDetails;
        this.state = PENDING;
        this.metadata = new HashMap<>();
    }

    public static ResultWithEvents<PaymentMethod> create(PaymentMethodDetails paymentMethodDetails) {
        PaymentMethod paymentMethod = new PaymentMethod(paymentMethodDetails);
        DomainEvent domainEvent = PaymentMethodSavedEvent.builder()
                .customerId(paymentMethod.paymentMethodDetails.getCustomerId())
                .build();
        return ResultWithEvents.of(paymentMethod, List.of(domainEvent));
    }

    public List<DomainEvent> enable() {
        if(!PENDING.equals(this.state)) {
            throw new IllegalPaymentMethodStateTransitionException();
        }

        this.state = ENABLED;

        return List.of(new PaymentMethodStateTransitionEvent(PENDING.name(), ENABLED.name()));
    }

    public List<DomainEvent> disable() {
        if(!PENDING.equals(this.state)) {
            throw new IllegalPaymentMethodStateTransitionException();
        }

        this.state = DISABLED;

        return List.of(new PaymentMethodStateTransitionEvent(PENDING.name(), DISABLED.name()));
    }
}
