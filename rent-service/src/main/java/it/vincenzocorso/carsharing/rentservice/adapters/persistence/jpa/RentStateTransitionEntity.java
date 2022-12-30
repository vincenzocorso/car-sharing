package it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
