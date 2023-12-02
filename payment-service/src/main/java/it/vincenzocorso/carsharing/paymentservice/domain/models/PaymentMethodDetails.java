package it.vincenzocorso.carsharing.paymentservice.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class PaymentMethodDetails {
    private final String customerId;
}
