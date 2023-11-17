package it.vincenzocorso.carsharing.customerservice.adapters.persistence.mongodb;

import io.quarkus.mongodb.panache.PanacheQuery;
import it.vincenzocorso.carsharing.customerservice.domain.models.Customer;
import it.vincenzocorso.carsharing.customerservice.domain.models.SearchCustomerCriteria;
import it.vincenzocorso.carsharing.customerservice.domain.ports.out.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class CustomerMongoRepositoryAdapter implements CustomerRepository {
	private final CustomerDocumentMapper customerMapper;

	public CustomerMongoRepositoryAdapter(CustomerDocumentMapper customerMapper) {
		this.customerMapper = customerMapper;
	}

	@Override
	public Customer save(Customer customer) {
		CustomerDocument customerDocument = this.customerMapper.convertToDocument(customer);
		customerDocument.persistOrUpdate();
		return this.customerMapper.convertFromDocument(customerDocument);
	}

	@Override
	public Optional<Customer> findById(String customerId) {
		Optional<CustomerDocument> customerDocument = CustomerDocument.findByIdOptional(new ObjectId(customerId));
		return customerDocument.map(this.customerMapper::convertFromDocument);
	}

	@Override
	public List<Customer> findByCriteria(SearchCustomerCriteria criteria) {
		PanacheQuery<CustomerDocument> customers = CustomerDocument.findAll();

		if(criteria.offset != null && criteria.limit != null)
			customers = customers.range(criteria.offset, criteria.offset + criteria.limit);

		return customers.list().stream()
				.map(this.customerMapper::convertFromDocument)
				.collect(Collectors.toList());
	}
}
