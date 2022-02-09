package it.vincenzocorso.carsharing.common.spring.messaging;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("it.vincenzocorso.carsharing.common.spring.messaging.outbox.jpa")
@EntityScan("it.vincenzocorso.carsharing.common.spring.messaging.outbox.jpa")
@ComponentScan("it.vincenzocorso.carsharing.common.spring.messaging.outbox.jpa")
public class OutboxMessagingConfig {
}
