package it.vincenzocorso.carsharing.paymentservice.adapters.persistence.jpa;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.SetupIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.SetupIntentCreateParams;
import it.vincenzocorso.carsharing.paymentservice.adapters.persistence.PaymentMethodWrapper;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethod;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethodDetails;
import it.vincenzocorso.carsharing.paymentservice.domain.ports.out.PaymentMethodRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.stripe.param.SetupIntentCreateParams.Usage.OFF_SESSION;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class PaymentMethodJPARepositoryAdapter implements PaymentMethodRepository {
    private static final String STRIPE_CLIENT_SECRET = "stripe__client_secret";

    final PaymentMethodEntityMapper entityMapper;
    final StripeClient stripeClient;

    @Override
    public Optional<PaymentMethod> findById(String paymentMethodId) {
        return PaymentMethodEntity.<PaymentMethodEntity>findByIdOptional(paymentMethodId)
                .map(entityMapper::convertFromEntity);
    }

    @Override
    public PaymentMethod save(PaymentMethod paymentMethod) {
        String customerId = paymentMethod.getPaymentMethodDetails().getCustomerId();
        Customer customer = this.retrieveStripeCustomer(customerId);

        SetupIntent setupIntent = null;
        if(!hasStripeSetupIntentBeenCreated(paymentMethod)) {
            setupIntent = createSetupIntent(paymentMethod.getPaymentMethodDetails(), customer);
            paymentMethod.getMetadata().put(STRIPE_CLIENT_SECRET, setupIntent.getClientSecret());
        }

        PaymentMethodEntity paymentMethodEntity = this.entityMapper.convertToEntity(paymentMethod);
        paymentMethodEntity.externalId = (setupIntent != null) ? setupIntent.getId() : null;
        paymentMethodEntity.persist();
        return this.entityMapper.convertFromEntity(paymentMethodEntity);
    }

    Customer retrieveStripeCustomer(String customerId) {
        return CustomerEntity.<CustomerEntity>findByIdOptional(customerId)
                .map(c -> {
                    try {
                        return this.stripeClient.customers().retrieve(c.externalCustomerId);
                    } catch (StripeException ex) {
                        log.error("An error occurred while retrieving the stripe customer (customerId: {}, externalCustomerId: {}): ", customerId, c.externalCustomerId, ex);
                        throw new RuntimeException(ex.getMessage());
                    }
                })
                .orElseGet(() -> {
                    try {
                        CustomerCreateParams params = CustomerCreateParams.builder()
                                .setName(customerId)
                                .build();
                        Customer customer = this.stripeClient.customers().create(params);
                        CustomerEntity customerEntity = new CustomerEntity();
                        customerEntity.customerId = customerId;
                        customerEntity.externalCustomerId = customer.getId();
                        customerEntity.persist();
                        return customer;
                    } catch (StripeException ex) {
                        log.error("An error occurred while creating the stripe customer (customerId: {}): ", customerId, ex);
                        throw new RuntimeException(ex.getMessage());
                    }
                });
    }

    private boolean hasStripeSetupIntentBeenCreated(PaymentMethod paymentMethod) {
        return paymentMethod instanceof PaymentMethodWrapper;
    }

    SetupIntent createSetupIntent(PaymentMethodDetails paymentMethodDetails, Customer customer) {
        try {
            SetupIntentCreateParams params = SetupIntentCreateParams.builder()
                    .addPaymentMethodType("sepa_debit")
                    .setCustomer(customer.getId())
                    .setUsage(OFF_SESSION)
                    .build();
            return stripeClient.setupIntents().create(params);
        } catch (StripeException ex) {
            log.error("An error occurred while creating the setup intent (details: {}): ", paymentMethodDetails, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }
}
