package it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
