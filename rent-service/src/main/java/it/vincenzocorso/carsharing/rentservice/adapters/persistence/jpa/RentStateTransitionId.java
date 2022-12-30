package it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RentStateTransitionId implements Serializable {
	@ManyToOne
	@JoinColumn(name = "rent")
	private RentEntity rent;

	@Column(name = "sequence_number")
	private Integer sequenceNumber;
}
