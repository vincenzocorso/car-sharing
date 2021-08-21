package it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "rent_state_transitions")
@NoArgsConstructor
@Getter
@Setter
public class RentStateTransitionEntity {
	@EmbeddedId
	private RentStateTransitionId id;

	@Column(name = "timestamp", nullable = false)
	private Instant timestamp;

	@Column(name = "state", nullable = false)
	private String state;
}
