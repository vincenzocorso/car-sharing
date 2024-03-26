package it.vincenzocorso.carsharing.rentservice.adapters.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.vincenzocorso.carsharing.rentservice.config.WebConfig;
import it.vincenzocorso.carsharing.rentservice.domain.exceptions.RentNotFoundException;
import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.SearchRentCriteria;
import it.vincenzocorso.carsharing.rentservice.domain.ports.in.RentVehicle;
import it.vincenzocorso.carsharing.rentservice.domain.ports.in.SearchRent;
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
import java.util.UUID;

import static it.vincenzocorso.carsharing.rentservice.domain.models.RandomRent.randomRent;
import static it.vincenzocorso.carsharing.rentservice.domain.models.RentState.*;
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
	private RentVehicle rentVehicle;

	@MockBean
	private SearchRent searchRent;

	@ParameterizedTest
	@ValueSource(strings = {"", "?limit=150", "?offset=0", "?states=PENDING,REJECTED,ACCEPTED,STARTED,ENDED"})
	void shouldGetRents(String queryParameters) throws Exception {
		Rent rent1 = randomRent(ENDED);
		Rent rent2 = randomRent(PENDING);
		when(this.searchRent.getRents(any(SearchRentCriteria.class))).thenReturn(List.of(rent1, rent2));

		this.mockMvc.perform(get("/rents" + queryParameters))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].rentId").value(rent1.getId()))
				.andExpect(jsonPath("$[0].customerId").value(rent1.getDetails().customerId()))
				.andExpect(jsonPath("$[0].vehicleId").value(rent1.getDetails().vehicleId()))
				.andExpect(jsonPath("$[0].state").value(ENDED.toString()))
				.andExpect(jsonPath("$[0].acceptedAt").value(rent1.getStateTransitions().get(1).getTimestamp().toString()))
				.andExpect(jsonPath("$[0].startedAt").value(rent1.getStateTransitions().get(2).getTimestamp().toString()))
				.andExpect(jsonPath("$[0].endedAt").value(rent1.getStateTransitions().get(3).getTimestamp().toString()))
				.andExpect(jsonPath("$[1].rentId").value(rent2.getId()))
				.andExpect(jsonPath("$[1].customerId").value(rent2.getDetails().customerId()))
				.andExpect(jsonPath("$[1].vehicleId").value(rent2.getDetails().vehicleId()))
				.andExpect(jsonPath("$[1].state").value(PENDING.toString()))
				.andExpect(jsonPath("$[1].acceptedAt").doesNotExist())
				.andExpect(jsonPath("$[1].startedAt").doesNotExist())
				.andExpect(jsonPath("$[1].endedAt").doesNotExist());
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
		Rent rent = randomRent(ENDED);
		when(this.searchRent.getRent(rent.getId())).thenReturn(rent);

		this.mockMvc.perform(get("/rents/" + rent.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.rentId").value(rent.getId()))
				.andExpect(jsonPath("$.customerId").value(rent.getDetails().customerId()))
				.andExpect(jsonPath("$.vehicleId").value(rent.getDetails().vehicleId()))
				.andExpect(jsonPath("$.state").value(ENDED.toString()))
				.andExpect(jsonPath("$.acceptedAt").value(rent.getStateTransitions().get(1).getTimestamp().toString()))
				.andExpect(jsonPath("$.startedAt").value(rent.getStateTransitions().get(2).getTimestamp().toString()))
				.andExpect(jsonPath("$.endedAt").value(rent.getStateTransitions().get(3).getTimestamp().toString()));
	}

	@Test
	void shouldNotGetRent() throws Exception {
		String rentId = UUID.randomUUID().toString();
		when(this.searchRent.getRent(rentId)).thenThrow(RentNotFoundException.class);

		this.mockMvc.perform(get("/rents/" + rentId))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void shouldCreateRent() throws Exception {
		Rent persistedRent = randomRent(PENDING);
		String customerId = persistedRent.getDetails().customerId();
		String vehicleId = persistedRent.getDetails().vehicleId();
		String request = this.objectMapper.writeValueAsString(new CreateRentRequest(customerId, vehicleId));
		when(this.rentVehicle.createRent(customerId, vehicleId)).thenReturn(persistedRent);

		this.mockMvc.perform(post("/rents").content(request).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.rentId").value(persistedRent.getId()))
				.andExpect(jsonPath("$.customerId").value(customerId))
				.andExpect(jsonPath("$.vehicleId").value(vehicleId))
				.andExpect(jsonPath("$.state").value(PENDING.toString()))
				.andExpect(jsonPath("$.acceptedAt").doesNotExist())
				.andExpect(jsonPath("$.startedAt").doesNotExist())
				.andExpect(jsonPath("$.endedAt").doesNotExist());
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "{}"})
	void shouldNotCreateRentWhenRequestBodyIsNotValid(String request) throws Exception {
		when(this.rentVehicle.createRent(any(), any())).thenThrow(RentNotFoundException.class);

		this.mockMvc.perform(post("/rents").content(request).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
}
