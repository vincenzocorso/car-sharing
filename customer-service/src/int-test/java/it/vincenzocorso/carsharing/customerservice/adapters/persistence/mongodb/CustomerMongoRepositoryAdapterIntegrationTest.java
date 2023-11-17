package it.vincenzocorso.carsharing.customerservice.adapters.persistence.mongodb;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.quarkus.test.junit.QuarkusTest;
import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import it.vincenzocorso.carsharing.customerservice.domain.models.SearchCustomerCriteria;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mongodb.assertions.Assertions.assertTrue;
import static it.vincenzocorso.carsharing.customerservice.domain.FakeCustomer.*;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@QuarkusTestResource(CustomerMongoRepositoryAdapterIntegrationTest.ContainerInitializer.class)
@Testcontainers
class CustomerMongoRepositoryAdapterIntegrationTest {
	public static class ContainerInitializer implements QuarkusTestResourceLifecycleManager {
		@Override
		public Map<String, String> start() {
			mongoDBContainer.start();
			Map<String, String> map = new HashMap<>();
			map.put("quarkus.mongodb.connection-string", "mongodb://" + mongoDBContainer.getContainerIpAddress() + ":" + mongoDBContainer.getFirstMappedPort() + "/customers");
			map.put("quarkus.mongodb.database", "customers");
			return map;
		}

		@Override
		public void stop() {
			mongoDBContainer.stop();
		}
	}

	@Container
	private static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.8"));

	@Inject
	CustomerMongoRepositoryAdapter customerMongoRepositoryAdapter;

	@AfterEach
	void cleanup() {
		CustomerDocument.deleteAll();
	}

	@Test
	void shouldFindCustomerById() {
		this.customerMongoRepositoryAdapter.save(CUSTOMER);

		Optional<Customer> optionalCustomer = this.customerMongoRepositoryAdapter.findById(CUSTOMER_ID);

		assertTrue(optionalCustomer.isPresent());
		Customer retrievedCustomer = optionalCustomer.get();
		assertEqualsWithCustomer(retrievedCustomer);
	}

	@Test
	void shouldFindAllCustomers() {
		List<Customer> savedCustomers = this.initializeCustomersTable();
		List<String> expectedCustomersIds = savedCustomers.stream().map(Customer::getId).collect(Collectors.toList());
		SearchCustomerCriteria searchCriteria = SearchCustomerCriteria.empty();

		List<String> retrievedCustomersIds = this.customerMongoRepositoryAdapter.findByCriteria(searchCriteria).stream().map(Customer::getId).collect(Collectors.toList());

		assertThat(retrievedCustomersIds).hasSameElementsAs(expectedCustomersIds);
	}

	@Test
	void shouldFindCustomersByPagination() {
		List<Customer> savedCustomers = this.initializeCustomersTable();
		SearchCustomerCriteria searchCustomerCriteria = SearchCustomerCriteria.builder().offset(1).limit(2).build();

		List<String> retrievedCustomersIds = this.customerMongoRepositoryAdapter.findByCriteria(searchCustomerCriteria).stream().map(Customer::getId).collect(Collectors.toList());

		assertThat(retrievedCustomersIds).hasSize(savedCustomers.size() - 1);
	}

	@Test
	void shouldSave() {
		Customer savedCustomer = this.customerMongoRepositoryAdapter.save(CUSTOMER);

		assertThat(CustomerDocument.count()).isEqualTo(1);
		assertEqualsWithCustomer(savedCustomer);
	}

	private List<Customer> initializeCustomersTable() {
		return Stream.of(customerInState(true, true), customerInState(false, true), customerInState(false, false))
				.peek(customer -> customer.setId(null))
				.map(this.customerMongoRepositoryAdapter::save)
				.collect(Collectors.toList());
	}
}
