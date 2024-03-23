package it.vincenzocorso.carsharing.paymentservice.adapters.stripe;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SetupIntentMetadataKey {
    PAYMENT_METHOD_ID("internal__payment_method_id");

    private final String keyName;
}
