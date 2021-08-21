package it.vincenzocorso.carsharing.rentservice.adapters.persistence;

import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentDetails;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentStateTransition;
import lombok.Getter;

import java.util.List;

@Getter
public class RentWrapper extends Rent {
	private final Long version;

	public RentWrapper(String id, RentDetails details, List<RentStateTransition> transitions, Long version) {
		super(id, details, transitions);
		this.version = version;
	}
}
