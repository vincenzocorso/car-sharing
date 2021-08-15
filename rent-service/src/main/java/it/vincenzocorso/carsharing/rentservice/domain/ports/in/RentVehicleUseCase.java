package it.vincenzocorso.carsharing.rentservice.domain.ports.in;

import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;

public interface RentVehicleUseCase {
	Rent createRent(String customerId, String vehicleId);
	Rent cancelRent(String customerId, String rentId);
	Rent startRent(String customerId, String rentId);
	Rent endRent(String customerId, String rentId);
}
