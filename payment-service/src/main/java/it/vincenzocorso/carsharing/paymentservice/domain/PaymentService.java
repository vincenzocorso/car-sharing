package it.vincenzocorso.carsharing.paymentservice.domain;

import it.vincenzocorso.carsharing.common.messaging.events.ResultWithEvents;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethod;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethodDetails;
import it.vincenzocorso.carsharing.paymentservice.domain.ports.in.SavePaymentMethod;
import it.vincenzocorso.carsharing.paymentservice.domain.ports.out.PaymentMethodRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaymentService implements SavePaymentMethod {
    final PaymentMethodRepository paymentMethodRepository;

    @Override
    public PaymentMethod savePaymentMethod(PaymentMethodDetails details) {
        ResultWithEvents<PaymentMethod> resultWithEvents = PaymentMethod.create(details);
        PaymentMethod savedPaymentMethod = this.paymentMethodRepository.save(resultWithEvents.result);

        // publish events

        return savedPaymentMethod;
    }
}
