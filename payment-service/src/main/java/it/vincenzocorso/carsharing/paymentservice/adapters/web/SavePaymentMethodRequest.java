package it.vincenzocorso.carsharing.paymentservice.adapters.web;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record SavePaymentMethodRequest(
    @NotEmpty(message = "The customer id must not be null nor empty")
    String customerId
) {}
