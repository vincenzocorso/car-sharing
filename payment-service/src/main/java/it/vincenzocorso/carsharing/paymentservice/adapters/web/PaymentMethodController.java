package it.vincenzocorso.carsharing.paymentservice.adapters.web;

import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethod;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethodDetails;
import it.vincenzocorso.carsharing.paymentservice.domain.ports.in.SavePaymentMethod;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

import static jakarta.ws.rs.core.Response.Status.CREATED;

@AllArgsConstructor
public class PaymentMethodController implements PaymentMethodRestApi {
    final SavePaymentMethod savePaymentMethod;
    final PaymentMethodMapper paymentMethodMapper;

    @Override
    public Response getPaymentMethod(String paymentMethodId) {
        return null;
    }

    @Override
    @Transactional
    public Response savePaymentMethod(SavePaymentMethodRequest request) {
        PaymentMethodDetails paymentMethodDetails = this.paymentMethodMapper.convertFromDto(request);
        PaymentMethod paymentMethod = this.savePaymentMethod.savePaymentMethod(paymentMethodDetails);
        PaymentMethodResponse response = this.paymentMethodMapper.convertToDto(paymentMethod);
        return Response.status(CREATED).entity(response).build();
    }
}
