package it.vincenzocorso.carsharing.rentservice.adapters.persistence.jpa;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class CustomUUIDGenerator extends UUIDGenerator {
	private String entityName;

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) {
		this.entityName = params.getProperty(ENTITY_NAME);
		super.configure(type, params, serviceRegistry);
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) {
		Serializable id = session
				.getEntityPersister(this.entityName, object)
				.getIdentifier(object, session);

		return (id == null) ? super.generate(session, object) : id;
	}
}
