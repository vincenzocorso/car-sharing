package it.vincenzocorso.carsharing.rentservice.domain;

import it.vincenzocorso.carsharing.common.events.ResultWithEvents;
import it.vincenzocorso.carsharing.common.exceptions.NotAuthorizedException;
import it.vincenzocorso.carsharing.rentservice.domain.exceptions.RentNotFoundException;
import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentDetails;
import it.vincenzocorso.carsharing.rentservice.domain.models.SearchRentCriteria;
import it.vincenzocorso.carsharing.rentservice.domain.ports.in.RentVehicleUseCase;
import it.vincenzocorso.carsharing.rentservice.domain.ports.in.SearchRentUseCase;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.RentRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RentService implements RentVehicleUseCase, SearchRentUseCase {
	private final RentRepository rentRepository;

	@Override
	public Rent createRent(String customerId, String vehicleId) {
		RentDetails rentDetails = new RentDetails(customerId, vehicleId);
		ResultWithEvents<Rent> resultWithEvents = Rent.create(rentDetails);
		Rent savedRent = this.rentRepository.save(resultWithEvents.result);

		// TODO: start CreateRentSaga
		// TODO: publish events

		return savedRent;
	}

	@Override
	public Rent cancelRent(String customerId, String rentId) {
		Rent rent = this.rentRepository.findById(rentId).orElseThrow(() -> new RentNotFoundException(rentId));
		if(!rent.isOwnedBy(customerId)) throw new NotAuthorizedException();
		rent.cancel();
		Rent savedRent = this.rentRepository.save(rent);

		// TODO: start CancelRentSaga
		// TODO: publish events

		return savedRent;
	}

	@Override
	public Rent startRent(String customerId, String rentId) {
		Rent rent = this.rentRepository.findById(rentId).orElseThrow(() -> new RentNotFoundException(rentId));
		if(!rent.isOwnedBy(customerId)) throw new NotAuthorizedException();
		rent.start();
		Rent savedRent = this.rentRepository.save(rent);

		// TODO: start StartRentSaga
		// TODO: publish events

		return savedRent;
	}

	@Override
	public Rent endRent(String customerId, String rentId) {
		Rent rent = this.rentRepository.findById(rentId).orElseThrow(() -> new RentNotFoundException(rentId));
		if(!rent.isOwnedBy(customerId)) throw new NotAuthorizedException();
		rent.end();
		Rent savedRent = this.rentRepository.save(rent);

		// TODO: publish events

		return savedRent;
	}

	@Override
	public List<Rent> getRents(SearchRentCriteria criteria) {
		return this.rentRepository.findByCriteria(criteria);
	}

	@Override
	public Rent getRent(String rentId) {
		return this.rentRepository.findById(rentId).orElseThrow(() -> new RentNotFoundException(rentId));
	}
}
