package it.vincenzocorso.carsharing.paymentservice.adapters.persistence.jpa;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class CustomerEntity extends PanacheEntityBase {
    @Id
    @Column(name = "customer_id")
    public String customerId;

    @Column(name = "external_customer_id", nullable = false)
    public String externalCustomerId;
}
