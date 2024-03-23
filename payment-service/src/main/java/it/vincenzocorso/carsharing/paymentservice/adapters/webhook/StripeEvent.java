package it.vincenzocorso.carsharing.paymentservice.adapters.webhook;

public record StripeEvent(
    String id,
    String object,
    String apiVersion,
    String created,
    String type,
    StripeEventData data
) {}
