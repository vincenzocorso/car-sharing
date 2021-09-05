package it.vincenzocorso.carsharing.common.spring.messaging.outbox.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxJpaMessageRepository extends JpaRepository<OutboxMessageEntity, String> {
}
