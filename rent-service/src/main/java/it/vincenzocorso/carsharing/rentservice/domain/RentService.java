package it.vincenzocorso.carsharing.rentservice.domain;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;
import it.vincenzocorso.carsharing.common.messaging.events.DomainEventProducer;
import it.vincenzocorso.carsharing.common.messaging.events.ResultWithEvents;
import it.vincenzocorso.carsharing.rentservice.domain.exceptions.RentNotFoundException;
import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentDetails;
import it.vincenzocorso.carsharing.rentservice.domain.models.SearchRentCriteria;
import it.vincenzocorso.carsharing.rentservice.domain.ports.in.RentVehicle;
import it.vincenzocorso.carsharing.rentservice.domain.ports.in.SearchRent;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.RentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RentService implements RentVehicle, SearchRent {
	public static final String EVENTS_CHANNEL = "rent-service-events";
	private final RentRepository rentRepository;
	private final DomainEventProducer domainEventProducer;

	@Transactional
	@Override
	public Rent createRent(String customerId, String vehicleId) {
		RentDetails rentDetails = new RentDetails(customerId, vehicleId);
		ResultWithEvents<Rent> resultWithEvents = Rent.create(rentDetails);
		Rent savedRent = this.rentRepository.save(resultWithEvents.result);

		this.domainEventProducer.publish(EVENTS_CHANNEL, savedRent.getId(), resultWithEvents.events);

		return savedRent;
	}

	@Transactional
	@Override
	public Rent rejectRent(String rentId) {
		Rent rent = this.rentRepository.findById(rentId).orElseThrow(() -> new RentNotFoundException(rentId));
		List<DomainEvent> events = rent.reject();
		Rent savedRent = this.rentRepository.save(rent);

		this.domainEventProducer.publish(EVENTS_CHANNEL, savedRent.getId(), events);

		return savedRent;
	}

	@Transactional
	@Override
	public Rent acceptRent(String rentId) {
		Rent rent = this.rentRepository.findById(rentId).orElseThrow(() -> new RentNotFoundException(rentId));
		List<DomainEvent> events = rent.accept();
		Rent savedRent = this.rentRepository.save(rent);

		this.domainEventProducer.publish(EVENTS_CHANNEL, savedRent.getId(), events);

		return savedRent;
	}

	@Transactional
	@Override
	public Rent cancelRent(String rentId) {
		Rent rent = this.rentRepository.findById(rentId).orElseThrow(() -> new RentNotFoundException(rentId));
		List<DomainEvent> events = rent.cancel();
		Rent savedRent = this.rentRepository.save(rent);

		this.domainEventProducer.publish(EVENTS_CHANNEL, savedRent.getId(), events);

		return savedRent;
	}

	@Transactional
	@Override
	public Rent startRent(String rentId) {
		Rent rent = this.rentRepository.findById(rentId).orElseThrow(() -> new RentNotFoundException(rentId));
		List<DomainEvent> events = rent.start();
		Rent savedRent = this.rentRepository.save(rent);

		this.domainEventProducer.publish(EVENTS_CHANNEL, savedRent.getId(), events);

		return savedRent;
	}

	@Transactional
	@Override
	public Rent endRent(String rentId) {
		Rent rent = this.rentRepository.findById(rentId).orElseThrow(() -> new RentNotFoundException(rentId));
		List<DomainEvent> events = rent.end();
		Rent savedRent = this.rentRepository.save(rent);

		this.domainEventProducer.publish(EVENTS_CHANNEL, savedRent.getId(), events);

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
