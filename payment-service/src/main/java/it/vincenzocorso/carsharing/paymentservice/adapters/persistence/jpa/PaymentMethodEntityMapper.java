package it.vincenzocorso.carsharing.paymentservice.adapters.persistence.jpa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.vincenzocorso.carsharing.paymentservice.adapters.persistence.PaymentMethodWrapper;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethod;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethodDetails;
import it.vincenzocorso.carsharing.paymentservice.domain.models.PaymentMethodState;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class PaymentMethodEntityMapper {
    final ObjectMapper objectMapper;

    public PaymentMethodEntity convertToEntity(PaymentMethod paymentMethod) {
        PaymentMethodEntity paymentMethodEntity = new PaymentMethodEntity();
        paymentMethodEntity.id = paymentMethod.getId();
        paymentMethodEntity.customerId = paymentMethod.getPaymentMethodDetails().getCustomerId();
        paymentMethodEntity.state = paymentMethod.getState().name();
        paymentMethodEntity.metadata = encodeMetadata(paymentMethod.getMetadata());
        if(paymentMethod instanceof PaymentMethodWrapper wrapper) {
            paymentMethodEntity.externalId = wrapper.getExternalId();
            paymentMethodEntity.version = wrapper.getVersion();
        }
        return paymentMethodEntity;
    }

    public String encodeMetadata(Map<String, String> metadata) {
        try {
            return objectMapper.writeValueAsString(metadata);
        } catch (Exception ex) {
            log.error("An error occurred while encoding payment method metadata: ", ex);
            throw new RuntimeException(ex);
        }
    }

    public PaymentMethod convertFromEntity(PaymentMethodEntity paymentMethodEntity) {
        String id = paymentMethodEntity.id;
        PaymentMethodDetails paymentMethodDetails = PaymentMethodDetails.builder()
                .customerId(paymentMethodEntity.customerId)
                .build();
        PaymentMethodState state = PaymentMethodState.valueOf(paymentMethodEntity.state);
        Map<String, String> metadata = decodeMetadata(paymentMethodEntity.metadata);
        String externalId = paymentMethodEntity.externalId;
        Long version = paymentMethodEntity.version;
        return new PaymentMethodWrapper(id, paymentMethodDetails, state, metadata, externalId, version);
    }

    private Map<String, String> decodeMetadata(String encodedMetadata) {
        try {
            return objectMapper.readValue(encodedMetadata, new TypeReference<>() {});
        } catch (Exception ex) {
            log.error("An error occurred while decoding payment method metadata: ", ex);
            throw new RuntimeException(ex);
        }
    }
}
