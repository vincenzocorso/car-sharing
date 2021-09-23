package it.vincenzocorso.carsharing.customerservice.domain.models;

import lombok.Builder;

@Builder
public class SearchCustomerCriteria {
	public final Integer limit;
	public final Integer offset;

	public static SearchCustomerCriteria empty() {
		return SearchCustomerCriteria.builder().build();
	}
}
