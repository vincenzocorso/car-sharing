package it.vincenzocorso.carsharing.rentservice.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RentNotFoundException extends RuntimeException {
	private final String rentId;
}
