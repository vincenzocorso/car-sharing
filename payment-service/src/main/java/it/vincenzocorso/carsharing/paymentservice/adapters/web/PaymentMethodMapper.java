package it.vincenzocorso.carsharing.paymentservice.adapters.web;

import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethod;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethodDetails;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentMethodMapper {
    public PaymentMethodResponse convertToDto(PaymentMethod paymentMethod) {
        return PaymentMethodResponse.builder()
                .paymentMethodId(paymentMethod.getId())
                .customerId(paymentMethod.getPaymentMethodDetails().getCustomerId())
                .state(paymentMethod.getState())
                .metadata(paymentMethod.getMetadata())
                .build();
    }

    public PaymentMethodDetails convertFromDto(SavePaymentMethodRequest request) {
        return PaymentMethodDetails.builder()
                .customerId(request.customerId())
                .build();
    }
}
