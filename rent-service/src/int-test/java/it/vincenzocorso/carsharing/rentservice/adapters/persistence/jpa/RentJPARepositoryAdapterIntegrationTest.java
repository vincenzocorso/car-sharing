package it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa;

import it.vincenzocorso.carsharing.common.messaging.events.ResultWithEvents;
import it.vincenzocorso.carsharing.rentservice.adapters.persistence.RentWrapper;
import it.vincenzocorso.carsharing.rentservice.domain.models.RandomRent;
import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentState;
import it.vincenzocorso.carsharing.rentservice.domain.models.SearchRentCriteria;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.vincenzocorso.carsharing.rentservice.domain.models.RandomRent.randomRent;
import static it.vincenzocorso.carsharing.rentservice.domain.models.RentState.PENDING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class RentJPARepositoryAdapterIntegrationTest {
	@Container
	private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13-alpine");

	@DynamicPropertySource
	static void setupDatasource(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
		registry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
		registry.add("spring.flyway.defaultSchema", () -> "public");
	}

	@Autowired
	private RentJPARepository rentRepository;

	@Autowired
	private RentJPARepositoryAdapter rentJPARepositoryAdapter;

	@AfterEach
	void cleanUp() {
		this.rentRepository.deleteAll();
	}

	@Test
	void shouldSave() {
		Rent rent = randomRent();
		rent.setId(null);

		Rent savedRent = this.rentJPARepositoryAdapter.save(rent);

		assertThat(this.rentRepository.count()).isEqualTo(1);
		assertThat(savedRent).isInstanceOf(RentWrapper.class);
		assertThat(((RentWrapper)savedRent).getVersion()).isZero();
		assertThat(savedRent)
				.usingRecursiveComparison()
				.ignoringFields("id", "version")
				.isEqualTo(rent);
	}

	@Test
	void shouldGenerateUUID() {
		Rent newRent = randomRent();
		newRent.setId(null);

		Rent savedRent = this.rentJPARepositoryAdapter.save(newRent);

		assertDoesNotThrow(() -> UUID.fromString(savedRent.getId()));
	}

	@Test
	void shouldFindAllRents() {
		List<Rent> savedRents = this.initializeRentsTable();
		List<String> expectedRentsIds = savedRents.stream().map(Rent::getId).collect(Collectors.toList());
		SearchRentCriteria searchCriteria = SearchRentCriteria.empty();

		List<String> retrievedRentsIds = this.rentJPARepositoryAdapter.findByCriteria(searchCriteria).stream().map(Rent::getId).collect(Collectors.toList());

		assertThat(retrievedRentsIds).hasSameElementsAs(expectedRentsIds);
	}

	@Test
	void shouldFindRentById() {
		Rent savedRent = this.rentJPARepositoryAdapter.save(randomRent());

		Optional<Rent> optionalRent = this.rentJPARepositoryAdapter.findById(savedRent.getId());

		assertTrue(optionalRent.isPresent());
		Rent retrievedRent = optionalRent.get();
		assertThat(retrievedRent).isInstanceOf(RentWrapper.class);
		assertThat(retrievedRent)
				.usingRecursiveComparison()
				.isEqualTo(savedRent);
	}

	@Test
	void shouldFindRentsByStateCriteria() {
		List<Rent> savedRents = this.initializeRentsTable();
		List<String> expectedStates = List.of(RentState.STARTED.toString(), RentState.ENDED.toString());
		List<String> expectedRentsIds = savedRents.stream().filter(s -> expectedStates.contains(s.getCurrentState().toString())).map(Rent::getId).collect(Collectors.toList());
		SearchRentCriteria searchCriteria = SearchRentCriteria.builder().states(expectedStates).build();

		List<String> retrievedRentsIds = this.rentJPARepositoryAdapter.findByCriteria(searchCriteria).stream().map(Rent::getId).collect(Collectors.toList());

		assertThat(retrievedRentsIds).hasSameElementsAs(expectedRentsIds);
	}

	@Test
	void shouldFindRentsByCustomerIdCriteria() {
		List<Rent> savedRents = this.initializeRentsTable();
		Collections.shuffle(savedRents);
		Rent expectedRent = savedRents.get(0);
		List<String> expectedRentsIds = Stream.of(expectedRent).map(Rent::getId).toList();
		SearchRentCriteria searchCriteria = SearchRentCriteria.builder().customerId(expectedRent.getDetails().customerId()).build();

		List<String> retrievedRentsIds = this.rentJPARepositoryAdapter.findByCriteria(searchCriteria).stream().map(Rent::getId).collect(Collectors.toList());

		assertThat(retrievedRentsIds).hasSameElementsAs(expectedRentsIds);
	}

	@Test
	void shouldFindRentsByPagination() {
		List<Rent> savedRents = this.initializeRentsTable();
		SearchRentCriteria searchCriteria = SearchRentCriteria.builder().offset(1).limit(2).build();

		List<String> retrievedRentsIds = this.rentJPARepositoryAdapter.findByCriteria(searchCriteria).stream().map(Rent::getId).collect(Collectors.toList());

		assertThat(retrievedRentsIds).hasSize(savedRents.size() - 1);
	}

	@Test
	void shouldThrowExceptionWhenOptimisticLockFails() {
		Rent rent = this.rentJPARepositoryAdapter.save(randomRent(PENDING));
		Rent loadedRent1 = this.rentJPARepositoryAdapter.findById(rent.getId()).orElseThrow();
		Rent loadedRent2 = this.rentJPARepositoryAdapter.findById(rent.getId()).orElseThrow();
		loadedRent1.accept();
		loadedRent2.reject();

		Rent savedRent = this.rentJPARepositoryAdapter.save(loadedRent1);
		assertThatException().isThrownBy(() -> this.rentJPARepositoryAdapter.save(loadedRent2));
		assertThat(((RentWrapper)savedRent).getVersion()).isEqualTo(((RentWrapper)loadedRent1).getVersion() + 1);
	}

	private List<Rent> initializeRentsTable() {
		RentEntityMapper rentMapper = new RentEntityMapper();
		return Stream.of(PENDING, RentState.STARTED, RentState.ENDED)
				.map(RandomRent::randomRent)
				.peek(rent -> rent.setId(null))
				.map(rentMapper::convertToEntity)
				.map(this.rentRepository::save)
				.map(rentMapper::convertFromEntity)
				.collect(Collectors.toList());
	}
}
