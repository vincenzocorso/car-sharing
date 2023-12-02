package it.vincenzocorso.carsharing.paymentservice.adapters.persistence;

import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethod;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethodDetails;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethodState;
import lombok.Getter;

import java.util.Map;

@Getter
public class PaymentMethodWrapper extends PaymentMethod {
    private final String externalId;
    private final Long version;

    public PaymentMethodWrapper(String id, PaymentMethodDetails paymentMethodDetails, PaymentMethodState state, Map<String, String> metadata, String externalId, Long version) {
        super(id, paymentMethodDetails, state, metadata);
        this.externalId = externalId;
        this.version = version;
    }
}
