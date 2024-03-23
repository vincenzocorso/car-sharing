package it.vincenzocorso.carsharing.paymentservice.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentMethodNotFoundException extends RuntimeException {
    private final String paymentMethodId;
}
