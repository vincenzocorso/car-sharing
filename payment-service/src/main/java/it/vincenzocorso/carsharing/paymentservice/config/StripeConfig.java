package it.vincenzocorso.carsharing.paymentservice.config;

import com.stripe.StripeClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

class StripeConfig {
    @ConfigProperty(name = "stripe.api.key")
    String stripeApiKey;

    @Produces @ApplicationScoped
    StripeClient stripeClient() {
        return StripeClient.builder()
                .setApiKey(stripeApiKey)
                .build();
    }
}
