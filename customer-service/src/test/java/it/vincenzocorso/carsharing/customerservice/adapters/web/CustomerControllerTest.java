package it.vincenzocorso.carsharing.customerservice.adapters.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import it.vincenzocorso.carsharing.customerservice.domain.exceptions.CustomerNotFoundException;
import it.vincenzocorso.carsharing.customerservice.domain.models.CustomerDetails;
import it.vincenzocorso.carsharing.customerservice.domain.models.SearchCustomerCriteria;
import it.vincenzocorso.carsharing.customerservice.domain.ports.in.RegisterCustomer;
import it.vincenzocorso.carsharing.customerservice.domain.ports.in.SearchCustomer;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static io.restassured.RestAssured.given;
import static it.vincenzocorso.carsharing.customerservice.adapters.web.FakeCustomerDto.CUSTOMER_RESPONSE;
import static it.vincenzocorso.carsharing.customerservice.adapters.web.FakeCustomerDto.REGISTER_CUSTOMER_REQUEST;
import static it.vincenzocorso.carsharing.customerservice.domain.FakeCustomer.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@QuarkusTest
class CustomerControllerTest {
	@InjectMock
	private SearchCustomer searchCustomer;

	@InjectMock
	private RegisterCustomer registerCustomer;

	@Inject
	ObjectMapper objectMapper;

	@ParameterizedTest
	@ValueSource(strings = {"", "?limit=150", "?offset=0"})
	void shouldGetCustomers(String queryParameters) {
		List<CustomerResponse> expectedResponse = List.of(CUSTOMER_RESPONSE);
		when(this.searchCustomer.getCustomers(any(SearchCustomerCriteria.class))).thenReturn(List.of(CUSTOMER));

		List<CustomerResponse> actualResponse = given()
				.when().get("/customers" + queryParameters)
				.then().assertThat()
				.statusCode(HttpStatus.SC_OK)
				.contentType(MediaType.APPLICATION_JSON)
				.extract().body().jsonPath().getList(".", CustomerResponse.class);

		assertThat(actualResponse)
				.usingRecursiveComparison()
				.isEqualTo(expectedResponse);
	}

	@ParameterizedTest
	@ValueSource(strings = {"?limit=0", "?limit=201", "?offset=-1"})
	void shouldNotGetCustomersWhenCriteriaAreNotValid(String queryParameters) {
		given()
				.when().get("/customers" + queryParameters)
				.then().assertThat()
				.statusCode(HttpStatus.SC_BAD_REQUEST)
				.contentType(MediaType.APPLICATION_JSON)
				.body("issues", Matchers.any(List.class));
	}

	@Test
	void shouldGetCustomer() {
		when(this.searchCustomer.getCustomer(CUSTOMER_ID)).thenReturn(CUSTOMER);

		CustomerResponse actualCustomerResponse = given()
				.when().get("/customers/" + CUSTOMER_ID)
				.then().assertThat()
				.statusCode(HttpStatus.SC_OK)
				.contentType(MediaType.APPLICATION_JSON)
				.extract().body().as(CustomerResponse.class);

		assertThat(actualCustomerResponse)
				.usingRecursiveComparison()
				.isEqualTo(CUSTOMER_RESPONSE);
	}

	@Test
	void shouldNotGetCustomer() {
		when(this.searchCustomer.getCustomer(CUSTOMER_ID)).thenThrow(CustomerNotFoundException.class);

		given()
				.when().get("/customers/" + CUSTOMER_ID)
				.then().assertThat()
				.statusCode(HttpStatus.SC_NOT_FOUND)
				.contentType(MediaType.APPLICATION_JSON)
				.body("issues", Matchers.any(List.class));
	}

	@Test
	void shouldRegisterCustomer() throws Exception {
		String encodedRequest = this.objectMapper.writeValueAsString(REGISTER_CUSTOMER_REQUEST);
		when(this.registerCustomer.registerCustomer(any(CustomerDetails.class))).thenReturn(CUSTOMER);

		CustomerResponse actualResponse = given()
				.contentType(MediaType.APPLICATION_JSON)
				.body(encodedRequest)
				.when().post("/customers")
				.prettyPeek()
				.then().assertThat()
				.statusCode(HttpStatus.SC_CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.extract().body().as(CustomerResponse.class);

		assertThat(actualResponse)
				.usingRecursiveComparison()
				.isEqualTo(CUSTOMER_RESPONSE);
	}

	@Test
	void shouldNotRegisterCustomerWhenBodyIsNotValid() throws Exception {
		RegisterCustomerRequest customerRequest = RegisterCustomerRequest
				.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.fiscalCode(FISCAL_CODE)
				.build();
		String encodedRequest = this.objectMapper.writeValueAsString(customerRequest);

		given()
				.contentType(MediaType.APPLICATION_JSON)
				.body(encodedRequest)
				.when().post("/customers")
				.then().assertThat()
				.statusCode(HttpStatus.SC_BAD_REQUEST)
				.contentType(MediaType.APPLICATION_JSON)
				.body("issues", Matchers.any(List.class));
	}
}