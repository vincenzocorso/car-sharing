package it.vincenzocorso.carsharing.rentservice.domain;

import it.vincenzocorso.carsharing.rentservice.domain.exceptions.RentNotFoundException;
import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentState;
import it.vincenzocorso.carsharing.rentservice.domain.models.SearchRentCriteria;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.RentRepository;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.SagaManager;
import it.vincenzocorso.carsharing.rentservice.domain.sagas.CreateRentSaga;
import it.vincenzocorso.carsharing.rentservice.domain.sagas.CreateRentSagaState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static it.vincenzocorso.carsharing.rentservice.domain.FakeRent.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentServiceTest {
	@Mock
	private RentRepository rentRepository;

	@Mock
	private SagaManager sagaManager;

	@Mock
	private CreateRentSaga createRentSaga;

	@InjectMocks
	private RentService rentService;

	@Test
	void shouldCreateRent() {
		when(this.rentRepository.save(any(Rent.class))).then(invocation -> {
			Rent rent = invocation.getArgument(0);
			rent.setId(RENT_ID);
			return rent;
		});
		doNothing().when(this.sagaManager).startSaga(any(CreateRentSaga.class), any(CreateRentSagaState.class));

		Rent createdRent = this.rentService.createRent(CUSTOMER_ID, VEHICLE_ID);

		verify(this.rentRepository).save(createdRent);
		verify(this.sagaManager).startSaga(any(CreateRentSaga.class), any(CreateRentSagaState.class));
		// TODO: verify that the events have been published
	}

	@Test
	void shouldRejectRent() {
		Rent persistedRent = rentInState(RentState.PENDING);
		when(this.rentRepository.findById(RENT_ID)).thenReturn(Optional.of(persistedRent));
		when(this.rentRepository.save(any(Rent.class))).thenReturn(persistedRent);

		Rent rejectedRent = this.rentService.rejectRent(RENT_ID);

		verify(this.rentRepository).save(rejectedRent);
		// TODO: verify that the events have been published
	}

	@Test
	void shouldCancelRent() {
		Rent persistedRent = rentInState(RentState.ACCEPTED);
		when(this.rentRepository.findById(RENT_ID)).thenReturn(Optional.of(persistedRent));
		when(this.rentRepository.save(any(Rent.class))).thenReturn(persistedRent);

		Rent cancelledRent = this.rentService.cancelRent(RENT_ID);

		verify(this.rentRepository).save(cancelledRent);
		// TODO: verify that the events have been published
	}

	@Test
	void shouldNotCancelRentWhenRentIdDoesntExists() {
		when(this.rentRepository.findById(anyString())).thenReturn(Optional.empty());

		assertThrows(RentNotFoundException.class, () -> this.rentService.cancelRent("NotExistingId"));
	}

	@Test
	void shouldStartRent() {
		Rent persistedRent = rentInState(RentState.ACCEPTED);
		when(this.rentRepository.findById(RENT_ID)).thenReturn(Optional.of(persistedRent));
		when(this.rentRepository.save(any(Rent.class))).thenReturn(persistedRent);

		Rent startedRent = this.rentService.startRent(RENT_ID);

		verify(this.rentRepository).save(startedRent);
		// TODO: verify that the events have been published
	}

	@Test
	void shouldNotStartRentWhenRentIdDoesntExists() {
		when(this.rentRepository.findById(anyString())).thenReturn(Optional.empty());

		assertThrows(RentNotFoundException.class, () -> this.rentService.startRent("NotExistingId"));
	}

	@Test
	void shouldEndRent() {
		Rent persistedRent = rentInState(RentState.STARTED);
		when(this.rentRepository.findById(RENT_ID)).thenReturn(Optional.of(persistedRent));
		when(this.rentRepository.save(any(Rent.class))).thenReturn(persistedRent);

		Rent endedRent = this.rentService.endRent(RENT_ID);

		verify(this.rentRepository).save(endedRent);
		// TODO: verify that the events have been published
	}

	@Test
	void shouldNotEndRentWhenRentIdDoesntExists() {
		when(this.rentRepository.findById(anyString())).thenReturn(Optional.empty());

		assertThrows(RentNotFoundException.class, () -> this.rentService.endRent("NotExistingId"));
	}

	@Test
	void shouldGetRents() {
		List<Rent> persistedRents = List.of(rentInState(RentState.PENDING));
		SearchRentCriteria searchRentCriteria = SearchRentCriteria.empty();
		when(this.rentRepository.findByCriteria(searchRentCriteria)).thenReturn(persistedRents);

		List<Rent> retrievedRents = this.rentService.getRents(searchRentCriteria);

		assertThat(retrievedRents).hasSameElementsAs(persistedRents);
	}

	@Test
	void shouldGetRent() {
		Rent persistedRent = rentInState(RentState.PENDING);
		when(this.rentRepository.findById(RENT_ID)).thenReturn(Optional.of(persistedRent));

		Rent retrievedRent = this.rentService.getRent(RENT_ID);

		assertEquals(retrievedRent, persistedRent);
	}

	@Test
	void shouldNotGetRent() {
		when(this.rentRepository.findById(anyString())).thenReturn(Optional.empty());

		assertThrows(RentNotFoundException.class, () -> this.rentService.getRent(RENT_ID));
	}
}