package it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rents")
@NoArgsConstructor
@Getter
@Setter
public class RentEntity {
	@Id
	@GeneratedValue
	@UuidGenerator
	@Column(name = "rent_id")
	private UUID id;

	@Column(name = "customer_id", nullable = false)
	private String customerId;

	@Column(name = "vehicle_id", nullable = false)
	private String vehicleId;

	@Column(name = "current_state", nullable = false)
	private String currentState;

	@Version
	@Column(name = "version", nullable = false)
	private Long version;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id.rent", cascade = CascadeType.ALL)
	private List<RentStateTransitionEntity> stateTransitions = new ArrayList<>();
}