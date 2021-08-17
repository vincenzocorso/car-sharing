package it.vincenzocorso.carsharing.rentservice.domain.models;

import lombok.Builder;

import java.util.List;

@Builder
public class SearchRentCriteria {
	public final String customerId;
	public final List<String> states;
	public final Integer limit;
	public final Integer offset;

	public static SearchRentCriteria empty() {
		return new SearchRentCriteriaBuilder().build();
	}
}
