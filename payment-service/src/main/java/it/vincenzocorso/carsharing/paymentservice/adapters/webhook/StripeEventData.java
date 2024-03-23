package it.vincenzocorso.carsharing.paymentservice.adapters.webhook;

public record StripeEventData(
    StripeEventDataObject object
) {}
