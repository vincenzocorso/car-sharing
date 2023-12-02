package it.vincenzocorso.carsharing.paymentservice.domain.ports.in;

import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethod;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethodDetails;

public interface SavePaymentMethod {
    PaymentMethod savePaymentMethod(PaymentMethodDetails details);
}
