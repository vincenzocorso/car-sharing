package it.vincenzocorso.carsharing.rentservice.domain.exceptions;

public class RentNotFoundException extends RuntimeException {
	private final String rentId;

	public RentNotFoundException(String rentId) {
		super();
		this.rentId = rentId;
	}

	public String getRentId() {
		return this.rentId;
	}
}
