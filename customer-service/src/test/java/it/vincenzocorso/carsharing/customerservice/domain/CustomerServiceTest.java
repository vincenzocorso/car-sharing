package it.vincenzocorso.carsharing.customerservice.domain;

import it.vincenzocorso.carsharing.common.messaging.events.DomainEvent;
import it.vincenzocorso.carsharing.common.messaging.events.DomainEventProducer;
import it.vincenzocorso.carsharing.customerservice.domain.events.CustomerRegisteredEvent;
import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import it.vincenzocorso.carsharing.customerservice.domain.models.SearchCustomerCriteria;
import it.vincenzocorso.carsharing.customerservice.domain.ports.out.CustomerRepository;
import it.vincenzocorso.carsharing.customerservice.domain.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static it.vincenzocorso.carsharing.customerservice.domain.FakeCustomer.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private DomainEventProducer domainEventProducer;

	@InjectMocks
	private CustomerService customerService;

	@Test
	void shouldRegisterCustomer() {
		DomainEvent event = CustomerRegisteredEvent.builder()
			.firstName(FIRST_NAME)
			.lastName(LAST_NAME)
			.dateOfBirth(DATE_OF_BIRTH.toString())
			.fiscalCode(FISCAL_CODE)
			.email(EMAIL)
			.phoneNumber(PHONE_NUMBER)
			.build();
		when(this.customerRepository.save(any(Customer.class))).then(invocation -> {
			Customer customer = invocation.getArgument(0);
			customer.setId(CUSTOMER_ID);
			return customer;
		});

		Customer registeredCustomer = this.customerService.registerCustomer(CUSTOMER_DETAILS);

		verify(this.customerRepository).save(registeredCustomer);
		verify(this.domainEventProducer).publish(CustomerService.EVENTS_CHANNEL, CUSTOMER_ID, List.of(event));
	}

	@Test
	void shouldGetCustomer() {
		when(this.customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(CUSTOMER));

		Customer retrievedCustomer = this.customerService.getCustomer(CUSTOMER_ID);

		assertEqualsWithCustomer(retrievedCustomer);
	}

	@Test
	void shouldNotGetCustomer() {
		when(this.customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());

		assertThrows(CustomerNotFoundException.class, () -> this.customerService.getCustomer(CUSTOMER_ID));
	}

	@Test
	void shouldGetCustomers() {
		List<Customer> persistedCustomers = List.of(CUSTOMER);
		SearchCustomerCriteria criteria = SearchCustomerCriteria.empty();
		when(this.customerRepository.findByCriteria(criteria)).thenReturn(persistedCustomers);

		List<Customer> retrievedCustomers = this.customerService.getCustomers(criteria);

		assertThat(retrievedCustomers).hasSameElementsAs(persistedCustomers);
	}
}