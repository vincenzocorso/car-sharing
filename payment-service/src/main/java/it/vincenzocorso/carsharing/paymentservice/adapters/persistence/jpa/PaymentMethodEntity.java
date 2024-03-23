package it.vincenzocorso.carsharing.paymentservice.adapters.persistence.jpa;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_methods")
@NoArgsConstructor
public class PaymentMethodEntity {
    @Id
    @Column(name = "payment_method_id")
    public String id;

    @Column(name = "external_id")
    public String externalId;

    @Column(name = "customer_id")
    public String customerId;

    @Column(name = "state")
    public String state;

    @Column(name = "metadata")
    public String metadata;

    @Version
    @Column(name = "version")
    public Long version;
}
