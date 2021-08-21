package it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentJPARepository extends JpaRepository<RentEntity, String> {
}
