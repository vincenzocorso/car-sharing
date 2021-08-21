package it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rents")
@NoArgsConstructor
@Getter
@Setter
public class RentEntity {
	@Id
	@GeneratedValue(generator = "custom-uuid")
	@GenericGenerator(name = "custom-uuid", strategy = "it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa.CustomUUIDGenerator")
	@Column(name = "rent_id")
	private String id;

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