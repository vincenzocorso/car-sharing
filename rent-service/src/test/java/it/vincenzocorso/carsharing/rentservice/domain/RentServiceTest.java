package it.vincenzocorso.carsharing.rentservice.domain;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;
import it.vincenzocorso.carsharing.common.messaging.events.DomainEventProducer;
import it.vincenzocorso.carsharing.rentservice.domain.events.RentCreatedEvent;
import it.vincenzocorso.carsharing.rentservice.domain.events.RentStateTransitionEvent;
import it.vincenzocorso.carsharing.rentservice.domain.exceptions.RentNotFoundException;
import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.SearchRentCriteria;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.RentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static it.vincenzocorso.carsharing.rentservice.domain.models.RandomRent.*;
import static it.vincenzocorso.carsharing.rentservice.domain.models.RentState.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentServiceTest {
	@Mock
	private RentRepository rentRepository;

	@Mock
	private DomainEventProducer domainEventProducer;

	@InjectMocks
	private RentService rentService;

	@Test
	void shouldCreateRent() {
		String rentId = UUID.randomUUID().toString();
		String customerId = UUID.randomUUID().toString();
		String vehicleId = UUID.randomUUID().toString();
		DomainEvent event = new RentCreatedEvent(customerId, vehicleId);
		when(this.rentRepository.save(any(Rent.class))).then(invocation -> {
			Rent rent = invocation.getArgument(0);
			rent.setId(rentId);
			return rent;
		});

		Rent createdRent = this.rentService.createRent(customerId, vehicleId);

		verify(this.rentRepository).save(createdRent);
		verify(this.domainEventProducer).publish(RentService.EVENTS_CHANNEL, rentId, List.of(event));
	}

	@Test
	void shouldRejectRent() {
		Rent persistedRent = randomRent(PENDING);
		DomainEvent event = new RentStateTransitionEvent(PENDING.toString(), REJECTED.toString());
		when(this.rentRepository.findById(persistedRent.getId())).thenReturn(Optional.of(persistedRent));
		when(this.rentRepository.save(any(Rent.class))).thenReturn(persistedRent);

		Rent rejectedRent = this.rentService.rejectRent(persistedRent.getId());

		verify(this.rentRepository).save(rejectedRent);
		verify(this.domainEventProducer).publish(RentService.EVENTS_CHANNEL, persistedRent.getId(), List.of(event));
	}

	@Test
	void shouldAcceptRent() {
		Rent persistedRent = randomRent(PENDING);
		DomainEvent event = new RentStateTransitionEvent(PENDING.toString(), ACCEPTED.toString());
		when(this.rentRepository.findById(persistedRent.getId())).thenReturn(Optional.of(persistedRent));
		when(this.rentRepository.save(any(Rent.class))).thenReturn(persistedRent);

		Rent acceptedRent = this.rentService.acceptRent(persistedRent.getId());

		verify(this.rentRepository).save(acceptedRent);
		verify(this.domainEventProducer).publish(RentService.EVENTS_CHANNEL, persistedRent.getId(), List.of(event));
	}

	@Test
	void shouldNotAcceptRentWhenRentDoesntExists() {
		when(this.rentRepository.findById(anyString())).thenReturn(Optional.empty());

		assertThatExceptionOfType(RentNotFoundException.class)
				.isThrownBy(() -> this.rentService.acceptRent("NotExistingId"));
	}

	@Test
	void shouldCancelRent() {
		Rent persistedRent = randomRent(ACCEPTED);
		DomainEvent event = new RentStateTransitionEvent(ACCEPTED.toString(), CANCELLED.toString());
		when(this.rentRepository.findById(persistedRent.getId())).thenReturn(Optional.of(persistedRent));
		when(this.rentRepository.save(any(Rent.class))).thenReturn(persistedRent);

		Rent cancelledRent = this.rentService.cancelRent(persistedRent.getId());

		verify(this.rentRepository).save(cancelledRent);
		verify(this.domainEventProducer).publish(RentService.EVENTS_CHANNEL, persistedRent.getId(), List.of(event));
	}

	@Test
	void shouldNotCancelRentWhenRentIdDoesntExists() {
		when(this.rentRepository.findById(anyString())).thenReturn(Optional.empty());

		assertThatExceptionOfType(RentNotFoundException.class)
				.isThrownBy(() -> this.rentService.cancelRent("NotExistingId"));
	}

	@Test
	void shouldStartRent() {
		Rent persistedRent = randomRent(ACCEPTED);
		DomainEvent event = new RentStateTransitionEvent(ACCEPTED.toString(), STARTED.toString());
		when(this.rentRepository.findById(persistedRent.getId())).thenReturn(Optional.of(persistedRent));
		when(this.rentRepository.save(any(Rent.class))).thenReturn(persistedRent);

		Rent startedRent = this.rentService.startRent(persistedRent.getId());

		verify(this.rentRepository).save(startedRent);
		verify(this.domainEventProducer).publish(RentService.EVENTS_CHANNEL, persistedRent.getId(), List.of(event));
	}

	@Test
	void shouldNotStartRentWhenRentIdDoesntExists() {
		when(this.rentRepository.findById(anyString())).thenReturn(Optional.empty());

		assertThatExceptionOfType(RentNotFoundException.class)
				.isThrownBy(() -> this.rentService.startRent("NotExistingId"));
	}

	@Test
	void shouldEndRent() {
		Rent persistedRent = randomRent(STARTED);
		DomainEvent event = new RentStateTransitionEvent(STARTED.toString(), ENDED.toString());
		when(this.rentRepository.findById(persistedRent.getId())).thenReturn(Optional.of(persistedRent));
		when(this.rentRepository.save(any(Rent.class))).thenReturn(persistedRent);

		Rent endedRent = this.rentService.endRent(persistedRent.getId());

		verify(this.rentRepository).save(endedRent);
		verify(this.domainEventProducer).publish(RentService.EVENTS_CHANNEL, persistedRent.getId(), List.of(event));
	}

	@Test
	void shouldNotEndRentWhenRentIdDoesntExists() {
		when(this.rentRepository.findById(anyString())).thenReturn(Optional.empty());

		assertThatExceptionOfType(RentNotFoundException.class)
				.isThrownBy(() -> this.rentService.endRent("NotExistingId"));
	}

	@Test
	void shouldGetRents() {
		List<Rent> persistedRents = List.of(randomRent(PENDING));
		SearchRentCriteria searchRentCriteria = SearchRentCriteria.empty();
		when(this.rentRepository.findByCriteria(searchRentCriteria)).thenReturn(persistedRents);

		List<Rent> retrievedRents = this.rentService.getRents(searchRentCriteria);

		assertThat(retrievedRents).hasSameElementsAs(persistedRents);
	}

	@Test
	void shouldGetRent() {
		Rent persistedRent = randomRent(PENDING);
		when(this.rentRepository.findById(persistedRent.getId())).thenReturn(Optional.of(persistedRent));

		Rent retrievedRent = this.rentService.getRent(persistedRent.getId());

		assertThat(retrievedRent).isEqualTo(persistedRent);
	}

	@Test
	void shouldNotGetRent() {
		String rentId = UUID.randomUUID().toString();
		when(this.rentRepository.findById(anyString())).thenReturn(Optional.empty());

		assertThatExceptionOfType(RentNotFoundException.class)
				.isThrownBy(() -> this.rentService.getRent(rentId));
	}
}