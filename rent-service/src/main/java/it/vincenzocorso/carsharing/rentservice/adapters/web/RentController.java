package it.vincenzocorso.carsharing.rentservice.adapters.web;

import it.vincenzocorso.carsharing.common.web.ErrorResponse;
import it.vincenzocorso.carsharing.common.web.ErrorResponses;
import it.vincenzocorso.carsharing.common.web.Issue;
import it.vincenzocorso.carsharing.rentservice.domain.exceptions.IllegalRentStateTransitionException;
import it.vincenzocorso.carsharing.rentservice.domain.exceptions.RentNotFoundException;
import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.SearchRentCriteria;
import it.vincenzocorso.carsharing.rentservice.domain.ports.in.RentVehicle;
import it.vincenzocorso.carsharing.rentservice.domain.ports.in.SearchRent;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@AllArgsConstructor
public class RentController implements RentRestApi {
	private final SearchRent searchRent;
	private final RentVehicle rentVehicle;

	private final RentMapper rentMapper;

	@Override
	public List<RentResponse> getRents(String customerId, List<String> states, Integer limit, Integer offset) {
		SearchRentCriteria searchRentCriteria = SearchRentCriteria.builder()
				.customerId(customerId)
				.states(states)
				.limit(limit)
				.offset(offset)
				.build();
		return this.searchRent.getRents(searchRentCriteria).stream().map(this.rentMapper::convertToDto).toList();
	}

	@Override
	public RentResponse getRent(String rentId) {
		Rent retrievedRent = this.searchRent.getRent(rentId);
		return this.rentMapper.convertToDto(retrievedRent);
	}

	@Override
	public RentResponse createRent(CreateRentRequest request) {
		Rent createdRent = this.rentVehicle.createRent(request.customerId(), request.vehicleId());
		return this.rentMapper.convertToDto(createdRent);
	}

	@ExceptionHandler(RentNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleRentNotFoundException(RentNotFoundException ex) {
		List<Issue> issues = List.of(new Issue("rentId", "The rent with id " + ex.getRentId() + " was not found"));
		return ErrorResponses.makeResourceNotFoundErrorResponse(issues);
	}

	@ExceptionHandler(IllegalRentStateTransitionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleIllegalRentStateTransitionException() {
		List<Issue> issues = List.of(new Issue("rent", "The operation can't be performed due to the current state of the rent"));
		return ErrorResponses.makeBadRequestErrorResponse(issues);
	}
}
