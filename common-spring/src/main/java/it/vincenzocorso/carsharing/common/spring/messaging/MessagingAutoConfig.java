package it.vincenzocorso.carsharing.common.spring.messaging;

import it.vincenzocorso.carsharing.common.spring.messaging.outbox.jpa.OutboxJpaMessagingConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(OutboxJpaMessagingConfig.class)
public class MessagingAutoConfig {
}
