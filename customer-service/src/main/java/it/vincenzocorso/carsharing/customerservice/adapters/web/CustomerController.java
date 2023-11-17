package it.vincenzocorso.carsharing.customerservice.adapters.web;

import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import it.vincenzocorso.carsharing.customerservice.domain.models.CustomerDetails;
import it.vincenzocorso.carsharing.customerservice.domain.models.SearchCustomerCriteria;
import it.vincenzocorso.carsharing.customerservice.domain.ports.in.RegisterCustomer;
import it.vincenzocorso.carsharing.customerservice.domain.ports.in.SearchCustomer;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.OK;

@AllArgsConstructor
public class CustomerController implements CustomerRestApi {
	private final SearchCustomer searchCustomer;
	private final RegisterCustomer registerCustomer;
	private final CustomerMapper customerMapper;

	@Override
	public Response getCustomer(String customerId) {
		Customer retrievedCustomer = this.searchCustomer.getCustomer(customerId);
		CustomerResponse responseBody = this.customerMapper.convertToDto(retrievedCustomer);
		return Response.status(OK).entity(responseBody).build();
	}

	@Override
	public Response getCustomers(Integer limit, Integer offset) {
		SearchCustomerCriteria criteria = SearchCustomerCriteria.builder().limit(limit).offset(offset).build();
		List<CustomerResponse> responseBody = this.searchCustomer.getCustomers(criteria).stream()
				.map(this.customerMapper::convertToDto)
				.toList();
		return Response.status(OK).entity(responseBody).build();
	}

	@Override
	public Response registerCustomer(RegisterCustomerRequest request) {
		CustomerDetails customerDetails = this.customerMapper.convertFromDto(request);
		Customer registeredCustomer = this.registerCustomer.registerCustomer(customerDetails);
		CustomerResponse responseBody = this.customerMapper.convertToDto(registeredCustomer);
		return Response.status(CREATED).entity(responseBody).build();
	}
}
