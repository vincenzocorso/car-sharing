package it.vincenzocorso.carsharing.rentservice.adapters.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.vincenzocorso.carsharing.rentservice.api.web.CreateRentRequest;
import it.vincenzocorso.carsharing.rentservice.config.WebConfig;
import it.vincenzocorso.carsharing.rentservice.domain.exceptions.RentNotFoundException;
import it.vincenzocorso.carsharing.rentservice.domain.models.RentState;
import it.vincenzocorso.carsharing.rentservice.domain.models.SearchRentCriteria;
import it.vincenzocorso.carsharing.rentservice.domain.ports.in.RentVehicleUseCase;
import it.vincenzocorso.carsharing.rentservice.domain.ports.in.SearchRentUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static it.vincenzocorso.carsharing.rentservice.domain.FakeRent.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RentController.class)
@Import({RentMapper.class, WebConfig.class})
class RentControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private RentVehicleUseCase rentVehicleUseCase;

	@MockBean
	private SearchRentUseCase searchRentUseCase;

	@ParameterizedTest
	@ValueSource(strings = {"", "?limit=150", "?offset=0", "?states=PENDING,REJECTED,ACCEPTED,STARTED,ENDED"})
	void shouldGetRents(String queryParameters) throws Exception {
		when(this.searchRentUseCase.getRents(any(SearchRentCriteria.class))).thenReturn(List.of(RENT));

		this.mockMvc.perform(get("/rents" + queryParameters))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].rentId").value(RENT_ID))
				.andExpect(jsonPath("$[0].customerId").value(CUSTOMER_ID))
				.andExpect(jsonPath("$[0].vehicleId").value(VEHICLE_ID))
				.andExpect(jsonPath("$[0].state").value(TRANSITION_4_STATE.toString()))
				.andExpect(jsonPath("$[0].acceptedAt").value(TRANSITION_2_TIMESTAMP.toString()))
				.andExpect(jsonPath("$[0].startedAt").value(TRANSITION_3_TIMESTAMP.toString()))
				.andExpect(jsonPath("$[0].endedAt").value(TRANSITION_4_TIMESTAMP.toString()));
	}

	@ParameterizedTest
	@ValueSource(strings = {"?limit=0", "?limit=201", "?offset=-1", "?states=PENDING,INVALID_STATE,ACCEPTED,STARTED,ENDED"})
	void shouldNotGetRentsWhenCriteriaAreNotValid(String queryParameters) throws Exception {
		this.mockMvc.perform(get("/rents" + queryParameters))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.issues").isArray())
				.andExpect(jsonPath("$.issues", hasSize(1)));
	}

	@Test
	void shouldGetRent() throws Exception {
		when(this.searchRentUseCase.getRent(RENT_ID)).thenReturn(RENT);

		this.mockMvc.perform(get("/rents/" + RENT_ID))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.rentId").value(RENT_ID))
				.andExpect(jsonPath("$.customerId").value(CUSTOMER_ID))
				.andExpect(jsonPath("$.vehicleId").value(VEHICLE_ID))
				.andExpect(jsonPath("$.state").value(TRANSITION_4_STATE.toString()))
				.andExpect(jsonPath("$.acceptedAt").value(TRANSITION_2_TIMESTAMP.toString()))
				.andExpect(jsonPath("$.startedAt").value(TRANSITION_3_TIMESTAMP.toString()))
				.andExpect(jsonPath("$.endedAt").value(TRANSITION_4_TIMESTAMP.toString()));
	}

	@Test
	void shouldNotGetRent() throws Exception {
		when(this.searchRentUseCase.getRent(RENT_ID)).thenThrow(RentNotFoundException.class);

		this.mockMvc.perform(get("/rents/" + RENT_ID))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void shouldCreateRent() throws Exception {
		String request = this.objectMapper.writeValueAsString(new CreateRentRequest(CUSTOMER_ID, VEHICLE_ID));
		when(this.rentVehicleUseCase.createRent(CUSTOMER_ID, VEHICLE_ID)).thenReturn(rentInState(RentState.PENDING));

		this.mockMvc.perform(post("/rents").content(request).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.rentId").value(RENT_ID))
				.andExpect(jsonPath("$.customerId").value(CUSTOMER_ID))
				.andExpect(jsonPath("$.vehicleId").value(VEHICLE_ID))
				.andExpect(jsonPath("$.state").value(RentState.PENDING.toString()))
				.andExpect(jsonPath("$.acceptedAt").doesNotExist())
				.andExpect(jsonPath("$.startedAt").doesNotExist())
				.andExpect(jsonPath("$.endedAt").doesNotExist());
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "{}"})
	void shouldNotCreateRentWhenRequestBodyIsNotValid(String request) throws Exception {
		when(this.rentVehicleUseCase.createRent(CUSTOMER_ID, VEHICLE_ID)).thenThrow(RentNotFoundException.class);

		this.mockMvc.perform(post("/rents").content(request).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
}
