package it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa;

import it.vincenzocorso.carsharing.rentservice.domain.models.Rent;
import it.vincenzocorso.carsharing.rentservice.domain.models.SearchRentCriteria;
import it.vincenzocorso.carsharing.rentservice.domain.ports.out.RentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class RentJPARepositoryAdapter implements RentRepository {
	private final RentJPARepository rentRepository;
	private final EntityManager entityManager;
	private final RentEntityMapper rentMapper;

	@Override
	public Optional<Rent> findById(String rentId) {
		return this.rentRepository.findById(rentId).map(this.rentMapper::convertFromEntity);
	}

	@Override
	public List<Rent> findByCriteria(SearchRentCriteria criteria) {
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<RentEntity> query = cb.createQuery(RentEntity.class);
		Root<RentEntity> root = query.from(RentEntity.class);

		List<Predicate> predicates = new ArrayList<>();
		if(criteria.customerId != null)
			predicates.add(cb.equal(root.get(RentEntity_.CUSTOMER_ID), criteria.customerId));
		if(criteria.states != null)
			predicates.add(root.get(RentEntity_.CURRENT_STATE).in(criteria.states));
		TypedQuery<RentEntity> typedQuery = this.entityManager.createQuery(query.where(predicates.toArray(Predicate[]::new)));

		if(criteria.offset != null && criteria.limit != null)
			typedQuery.setFirstResult(criteria.offset).setMaxResults(criteria.limit);

		return typedQuery.getResultList().stream().map(this.rentMapper::convertFromEntity).collect(Collectors.toList());
	}

	@Override
	public Rent save(Rent rent) {
		RentEntity rentEntity = this.rentMapper.convertToEntity(rent);
		RentEntity savedRentEntity = this.rentRepository.save(rentEntity);
		return this.rentMapper.convertFromEntity(savedRentEntity);
	}
}