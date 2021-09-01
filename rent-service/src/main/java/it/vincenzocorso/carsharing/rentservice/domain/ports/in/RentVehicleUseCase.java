package it.vincenzocorso.carsharing.rentservice.domain.ports.in;

import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;

public interface RentVehicleUseCase {
	Rent createRent(String customerId, String vehicleId);
	Rent rejectRent(String rentId);
	Rent cancelRent(String rentId);
	Rent startRent(String rentId);
	Rent endRent(String rentId);
}
