package it.vincenzocorso.carsharing.paymentservice.adapters.webhook;

import it.vincenzocorso.carsharing.common.exceptions.InternalServerException;
import it.vincenzocorso.carsharing.paymentservice.adapters.stripe.SetupIntentMetadataKey;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethod;
import it.vincenzocorso.carsharing.paymentservice.domain.ports.in.SavePaymentMethod;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.OK;

@Path("/stripe/webhook")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Slf4j
@AllArgsConstructor
public class StripeWebhookController {
    final SavePaymentMethod savePaymentMethod;

    @POST
    public Response handleStripeEvents(StripeEvent event) {
        log.info("Received event of type " + event.type() + ": " + event);

        switch (event.type()) {
            case "setup_intent.succeeded" -> handleSetupIntentSucceededEvent(event);
            default -> log.info("A stripe event was skipped: " + event);
        }

        return Response.status(OK).build();
    }

    @Transactional
    void handleSetupIntentSucceededEvent(StripeEvent event) {
        String paymentMethodId = Optional.ofNullable(event.data())
                .map(StripeEventData::object)
                .map(StripeEventDataObject::metadata)
                .map(metadata -> metadata.getOrDefault(SetupIntentMetadataKey.PAYMENT_METHOD_ID.getKeyName(), null))
                .orElseThrow(() -> new RuntimeException("Could not find the payment method id"));

        PaymentMethod enabledPaymentMethod = savePaymentMethod.enablePaymentMethod(paymentMethodId);
        log.info("Enabled payment method {}", enabledPaymentMethod.getId());
    }
}
