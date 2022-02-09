package it.vincenzocorso.carsharing.rentorchestratorservice.adapters.sagas.temporal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.vincenzocorso.carsharing.rentorchestratorservice.domain.sagas.CreateRentSagaState;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.CLASS,
		property = "@class",
		defaultImpl = Void.class)
@JsonSubTypes({
		@JsonSubTypes.Type(value = CreateRentSagaState.class)
})
public interface SagaStateJacksonMixIn {
}
