package it.vincenzocorso.carsharing.common.messaging.events;

import java.util.List;

public interface DomainEventProducer {
	void publish(String channel, String aggregateId, List<DomainEvent> event);
}
