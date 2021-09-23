package it.vincenzocorso.carsharing.customerservice.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerNotFoundException extends RuntimeException {
	private final String customerId;
}
