package it.vincenzocorso.carsharing.paymentservice.domain;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;
import it.vincenzocorso.carsharing.common.messaging.events.ResultWithEvents;
import it.vincenzocorso.carsharing.paymentservice.domain.exceptions.PaymentMethodNotFoundException;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethod;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethodDetails;
import it.vincenzocorso.carsharing.paymentservice.domain.ports.in.SavePaymentMethod;
import it.vincenzocorso.carsharing.paymentservice.domain.ports.out.PaymentMethodRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PaymentService implements SavePaymentMethod {
    final PaymentMethodRepository paymentMethodRepository;

    @Override
    public PaymentMethod savePaymentMethod(PaymentMethodDetails details) {
        ResultWithEvents<PaymentMethod> resultWithEvents = PaymentMethod.create(details);
        PaymentMethod savedPaymentMethod = this.paymentMethodRepository.save(resultWithEvents.result);

        // TODO: publish events

        return savedPaymentMethod;
    }

    @Override
    public PaymentMethod enablePaymentMethod(String paymentMethodId) {
        PaymentMethod paymentMethod = this.paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new PaymentMethodNotFoundException(paymentMethodId));
        List<DomainEvent> events = paymentMethod.enable();
        PaymentMethod savedPaymentMethod = paymentMethodRepository.save(paymentMethod);

        // TODO: publish events

        return savedPaymentMethod;
    }
}
