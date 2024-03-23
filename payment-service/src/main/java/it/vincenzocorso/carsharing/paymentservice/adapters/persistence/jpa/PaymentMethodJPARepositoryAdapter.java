package it.vincenzocorso.carsharing.paymentservice.adapters.persistence.jpa;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.SetupIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.SetupIntentCreateParams;
import it.vincenzocorso.carsharing.paymentservice.adapters.persistence.PaymentMethodWrapper;
import it.vincenzocorso.carsharing.paymentservice.adapters.stripe.SetupIntentMetadataKey;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethod;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethodDetails;
import it.vincenzocorso.carsharing.paymentservice.domain.ports.out.PaymentMethodRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.stripe.param.SetupIntentCreateParams.Usage.OFF_SESSION;
import static it.vincenzocorso.carsharing.paymentservice.adapters.stripe.SetupIntentMetadataKey.PAYMENT_METHOD_ID;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class PaymentMethodJPARepositoryAdapter implements PaymentMethodRepository {
    private static final String STRIPE_CLIENT_SECRET = "stripe__client_secret";

    final EntityManager entityManager;
    final PaymentMethodEntityMapper entityMapper;
    final StripeClient stripeClient;

    @Override
    public Optional<PaymentMethod> findById(String paymentMethodId) {
        return Optional.ofNullable(entityManager.find(PaymentMethodEntity.class, paymentMethodId))
                .map(entityMapper::convertFromEntity);
    }

    @Override
    public PaymentMethod save(PaymentMethod paymentMethod) {
        String customerId = paymentMethod.getPaymentMethodDetails().getCustomerId();
        Customer customer = this.retrieveStripeCustomer(customerId);

        PaymentMethodEntity paymentMethodEntity = this.entityMapper.convertToEntity(paymentMethod);

        if(!hasStripeSetupIntentBeenCreated(paymentMethod)) {
            paymentMethodEntity.id = UUID.randomUUID().toString();

            Map<SetupIntentMetadataKey, String> setupIntentMetadata = Map.ofEntries(Map.entry(PAYMENT_METHOD_ID, paymentMethodEntity.id));
            SetupIntent setupIntent = createSetupIntent(paymentMethod.getPaymentMethodDetails(), customer, setupIntentMetadata);

            paymentMethod.getMetadata().put(STRIPE_CLIENT_SECRET, setupIntent.getClientSecret());
            paymentMethodEntity.metadata = entityMapper.encodeMetadata(paymentMethod.getMetadata());
            paymentMethodEntity.externalId = setupIntent.getId();
        } else {
            paymentMethodEntity = entityManager.merge(paymentMethodEntity);
        }

        entityManager.persist(paymentMethodEntity);

        return this.entityMapper.convertFromEntity(paymentMethodEntity);
    }

    Customer retrieveStripeCustomer(String customerId) {
        return Optional.ofNullable(entityManager.find(CustomerEntity.class, customerId))
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
                        entityManager.persist(customerEntity);
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

    SetupIntent createSetupIntent(PaymentMethodDetails paymentMethodDetails, Customer customer, Map<SetupIntentMetadataKey, String> metadata) {
        try {
            SetupIntentCreateParams.Builder paramsBuilder = SetupIntentCreateParams.builder()
                    .addPaymentMethodType("sepa_debit")
                    .setCustomer(customer.getId())
                    .setUsage(OFF_SESSION);

            metadata.forEach((key, value) -> paramsBuilder.putMetadata(key.getKeyName(), value));

            return stripeClient.setupIntents().create(paramsBuilder.build());
        } catch (StripeException ex) {
            log.error("An error occurred while creating the setup intent (details: {}): ", paymentMethodDetails, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }
}
