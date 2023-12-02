package it.vincenzocorso.carsharing.paymentservice.adapters.web;

import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethodState;
import lombok.Builder;

import java.util.Map;

@Builder
public record PaymentMethodResponse(
    String paymentMethodId,
    String customerId,
    PaymentMethodState state,
    Map<String, String> metadata
) {}
