package it.vincenzocorso.carsharing.customerservice.adapters.web;

import it.vincenzocorso.carsharing.customerservice.api.web.CustomerResponse;
import it.vincenzocorso.carsharing.customerservice.domain.models.CustomerDetails;
import org.junit.jupiter.api.Test;

import static it.vincenzocorso.carsharing.customerservice.domain.FakeCustomer.*;
import static it.vincenzocorso.carsharing.customerservice.adapters.web.FakeCustomerDto.*;
import static org.assertj.core.api.Assertions.*;

class CustomerMapperTest {
	private final CustomerMapper customerMapper = new CustomerMapper();

	@Test
	void shouldConvertToDto() {
		CustomerResponse actualCustomerResponse = this.customerMapper.convertToDto(CUSTOMER);

		assertThat(actualCustomerResponse)
				.usingRecursiveComparison()
				.isEqualTo(CUSTOMER_RESPONSE);
	}

	@Test
	void shouldConvertFromDto() {
		CustomerDetails actualCustomerDetails = this.customerMapper.convertFromDto(REGISTER_CUSTOMER_REQUEST);

		assertThat(actualCustomerDetails)
				.usingRecursiveComparison()
				.isEqualTo(CUSTOMER_DETAILS);
	}
}