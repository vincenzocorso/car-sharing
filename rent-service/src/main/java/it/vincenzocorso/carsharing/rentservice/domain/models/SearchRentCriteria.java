package it.vincenzocorso.carsharing.rentservice.domain.models;

import lombok.Builder;

import java.util.List;

/**
 * This class represents the search criteria for rents
 */
@Builder
public class SearchRentCriteria {
	/**
	 * The id of the customer that started the rents to search for
	 */
	public final String customerId;

	/**
	 * The states of the rents to search for
	 */
	public final List<String> states;

	/**
	 * The maximum number of results to return
	 */
	public final Integer limit;

	/**
	 * The number of results to omit from the beginning
	 */
	public final Integer offset;

	public static SearchRentCriteria empty() {
		return new SearchRentCriteriaBuilder().build();
	}
}
