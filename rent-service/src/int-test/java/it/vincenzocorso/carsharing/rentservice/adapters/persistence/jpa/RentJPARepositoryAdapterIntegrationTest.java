package it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa;

import it.vincenzocorso.carsharing.rentservice.adapters.persistence.RentWrapper;
import it.vincenzocorso.carsharing.rentservice.domain.FakeRent;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.vincenzocorso.carsharing.rentservice.domain.FakeRent.*;
import static org.assertj.core.api.Assertions.assertThat;
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
		Rent rent = rentInState(RentState.STARTED);
		Rent savedRent = this.rentJPARepositoryAdapter.save(rent);
		rent.setId(savedRent.getId());

		assertThat(this.rentRepository.count()).isEqualTo(1);
		assertThat(savedRent).isInstanceOf(RentWrapper.class);
		assertEqualsWithRent(rent, savedRent);
	}

	@Test
	void shouldGenerateUUID() {
		Rent newRent = rentInState(RentState.PENDING);
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
		Rent savedRent = this.rentJPARepositoryAdapter.save(rentInState(RentState.STARTED));

		Optional<Rent> optionalRent = this.rentJPARepositoryAdapter.findById(savedRent.getId());

		assertTrue(optionalRent.isPresent());
		Rent retrievedRent = optionalRent.get();
		assertThat(retrievedRent).isInstanceOf(RentWrapper.class);
		assertEqualsWithRent(savedRent, retrievedRent);
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
		List<String> expectedRentsIds = savedRents.stream().map(Rent::getId).collect(Collectors.toList());
		SearchRentCriteria searchCriteria = SearchRentCriteria.builder().customerId(CUSTOMER_ID).build();

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

	private List<Rent> initializeRentsTable() {
		RentEntityMapper rentMapper = new RentEntityMapper();
		return Stream.of(RentState.PENDING, RentState.STARTED, RentState.ENDED)
				.map(FakeRent::rentInState)
				.peek(rent -> rent.setId(null))
				.map(rentMapper::convertToEntity)
				.map(this.rentRepository::save)
				.map(rentMapper::convertFromEntity)
				.collect(Collectors.toList());
	}
}
