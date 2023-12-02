package it.vincenzocorso.carsharing.paymentservice.adapters.persistence.jpa;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "payment_methods")
public class PaymentMethodEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_method_id")
    public String id;

    @Column(name = "external_id", nullable = false)
    public String externalId;

    @Column(name = "customer_id", nullable = false)
    public String customerId;

    @Column(name = "state", nullable = false)
    public String state;

    @Column(name = "metadata", nullable = false)
    public String metadata;

    @Version
    @Column(name = "version", nullable = false)
    public Long version;
}
