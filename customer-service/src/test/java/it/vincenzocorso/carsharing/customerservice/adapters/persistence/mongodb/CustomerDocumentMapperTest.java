package it.vincenzocorso.carsharing.customerservice.adapters.persistence.mongodb;

import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import org.junit.jupiter.api.Test;

import static it.vincenzocorso.carsharing.customerservice.adapters.persistence.mongodb.FakeCustomerDocument.*;
import static it.vincenzocorso.carsharing.customerservice.domain.FakeCustomer.*;
import static org.assertj.core.api.Assertions.*;

class CustomerDocumentMapperTest {
	private CustomerDocumentMapper customerMapper = new CustomerDocumentMapper();

	@Test
	void shouldConvertToDocument() {
		CustomerDocument actualCustomerDocument = this.customerMapper.convertToDocument(CUSTOMER);

		assertThat(actualCustomerDocument)
				.usingRecursiveComparison()
				.isEqualTo(CUSTOMER_DOCUMENT);
	}

	@Test
	void shouldConvertFromDocument() {
		Customer actualCustomer = this.customerMapper.convertFromDocument(CUSTOMER_DOCUMENT);

		assertThat(actualCustomer)
				.usingRecursiveComparison()
				.isEqualTo(CUSTOMER);
	}
}