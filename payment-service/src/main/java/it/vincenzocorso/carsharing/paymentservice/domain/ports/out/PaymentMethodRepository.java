package it.vincenzocorso.carsharing.paymentservice.domain.ports.out;

import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethod;

import java.util.Optional;

public interface PaymentMethodRepository {
    Optional<PaymentMethod> findById(String paymentMethodId);

    PaymentMethod save(PaymentMethod paymentMethod);
}
