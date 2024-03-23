package it.vincenzocorso.carsharing.paymentservice.adapters.webhook;

import java.util.Map;

public record StripeEventDataObject(
    String id,
    String object,
    Map<String, String> metadata
) {}
