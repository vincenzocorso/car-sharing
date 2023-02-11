package it.vincenzocorso.carsharing.customerservice.domain.models;

import lombok.Builder;

/**
 * This class represents the search criteria for customers
 */
@Builder
public class SearchCustomerCriteria {
	/**
	 * The maximum number of results to return
	 */
	public final Integer limit;

	/**
	 * The number of results to omit from the beginning
	 */
	public final Integer offset;

	public static SearchCustomerCriteria empty() {
		return SearchCustomerCriteria.builder().build();
	}
}
