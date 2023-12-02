package it.vincenzocorso.carsharing.paymentservice.config;

import it.vincenzocorso.carsharing.paymentservice.domain.PaymentService;
import it.vincenzocorso.carsharing.paymentservice.domain.ports.out.PaymentMethodRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

class BeansConfig {
    @Produces @ApplicationScoped
    PaymentService paymentService(PaymentMethodRepository paymentMethodRepository) {
        return new PaymentService(paymentMethodRepository);
    }
}
