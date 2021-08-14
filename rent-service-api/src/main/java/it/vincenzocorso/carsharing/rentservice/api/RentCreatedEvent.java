package it.vincenzocorso.carsharing.rentservice.api;

public class RentCreatedEvent implements RentDomainEvent {
	public final String customerId;
	public final String vehicleId;

	public RentCreatedEvent(String customerId, String vehicleId) {
		this.customerId = customerId;
		this.vehicleId = vehicleId;
	}
}
