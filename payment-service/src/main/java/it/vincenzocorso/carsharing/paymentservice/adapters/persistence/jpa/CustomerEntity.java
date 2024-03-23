package it.vincenzocorso.carsharing.paymentservice.adapters.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@NoArgsConstructor
public class CustomerEntity {
    @Id
    @Column(name = "customer_id")
    public String customerId;

    @Column(name = "external_customer_id", nullable = false)
    public String externalCustomerId;
}
