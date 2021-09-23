package it.vincenzocorso.carsharing.customerservice.adapters.web;

import it.vincenzocorso.carsharing.customerservice.api.web.CustomerResponse;
import it.vincenzocorso.carsharing.customerservice.api.web.CustomerRestApi;
import it.vincenzocorso.carsharing.customerservice.api.web.RegisterCustomerRequest;
import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import it.vincenzocorso.carsharing.customerservice.domain.models.CustomerDetails;
import it.vincenzocorso.carsharing.customerservice.domain.models.SearchCustomerCriteria;
import it.vincenzocorso.carsharing.customerservice.domain.ports.in.RegisterCustomerUseCase;
import it.vincenzocorso.carsharing.customerservice.domain.ports.in.SearchCustomerUseCase;
import lombok.AllArgsConstructor;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.*;

@Path(CustomerRestApi.PATH)
@AllArgsConstructor
public class CustomerController implements CustomerRestApi {
	private final SearchCustomerUseCase searchCustomerUseCase;
	private final RegisterCustomerUseCase registerCustomerUseCase;
	private final CustomerMapper customerMapper;

	@Override
	public Response getCustomer(String customerId) {
		Customer retrievedCustomer = this.searchCustomerUseCase.getCustomer(customerId);
		CustomerResponse responseBody = this.customerMapper.convertToDto(retrievedCustomer);
		return Response.status(OK).entity(responseBody).build();
	}

	@Override
	public Response getCustomers(Integer limit, Integer offset) {
		SearchCustomerCriteria criteria = SearchCustomerCriteria.builder().limit(limit).offset(offset).build();
		List<CustomerResponse> responseBody = this.searchCustomerUseCase.getCustomers(criteria).stream()
				.map(this.customerMapper::convertToDto)
				.collect(Collectors.toList());
		return Response.status(OK).entity(responseBody).build();
	}

	@Override
	public Response registerCustomer(RegisterCustomerRequest request) {
		CustomerDetails customerDetails = this.customerMapper.convertFromDto(request);
		Customer registeredCustomer = this.registerCustomerUseCase.registerCustomer(customerDetails);
		CustomerResponse responseBody = this.customerMapper.convertToDto(registeredCustomer);
		return Response.status(CREATED).entity(responseBody).build();
	}
}
