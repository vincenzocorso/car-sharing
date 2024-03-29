package it.vincenzocorso.carsharing.rentservice.adapters.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/rents")
public interface RentRestApi {
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	List<RentResponse> getRents(
			@RequestParam(name = "customerId", required = false) String customerId,
			@RequestParam(name = "states", required = false)
				List<@Pattern(regexp = "PENDING|REJECTED|ACCEPTED|STARTED|ENDED",
						message = "Each state must be one of the following: PENDING, REJECTED, ACCEPTED, STARTED, ENDED")
						String> states,
			@RequestParam(name = "limit", required = false, defaultValue = "10")
				@Min(value = 1, message = "The limit must be positive")
				@Max(value = 200, message = "The limit must be at most 200") Integer limit,
			@RequestParam(name = "offset", required = false, defaultValue = "0")
				@Min(value = 0, message = "The offset must be greater or equal to zero") Integer offset
	);

	@GetMapping("/{rentId}")
	@ResponseStatus(HttpStatus.OK)
	RentResponse getRent(@PathVariable(name = "rentId") String rentId);

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	RentResponse createRent(@Valid @RequestBody CreateRentRequest request);
}
